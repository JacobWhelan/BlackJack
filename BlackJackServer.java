// server class that also handles most game functions 

import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.concurrent.TimeUnit;

public class BlackJackServer {

// vector for "Address book" of users
public static Vector<ClientHandler> addressBook = new Vector<>();

public static Vector<ClientHandler> getClientHandlers() {
    return addressBook;
}

    public static void main(String[] args) throws IOException {
        // server is listening on port 5069
        ServerSocket ss = new ServerSocket(5069);

        Scanner input = new Scanner(System.in);
        System.out.println("How many players want to connect?");
        int playerMax = input.nextInt();
        input.close();

        System.out.println("Server running and looking for connections on port 5069...");

        // running infinite loop for getting client request
        while(true) {
            Socket socket = null;
            
            try {
                // socket object to receive incoming client requests
                socket = ss.accept();

                System.out.println("A new client is connected : " + socket);


                // obtaining input and out streams
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());         
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());                        
         

                System.out.println("Assigning new thread for this client");

                // create a new ClientHandler object
                ClientHandler CL = new ClientHandler(socket, ois, oos);

                // create a new thread object
                Thread t = new Thread(CL);

                // add user to Address Book
                addressBook.add(CL);

                // Invoking the start() method
                t.start();

                // decrement counter when player connects
                playerMax--;

                if(playerMax == 0) {
                    GamePlay.setup();
                    GamePlay.playGame();
                }


            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }

        }

        
        
    }

}

    
class ClientHandler implements Runnable {
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final Socket socket; 
    
    private String name;
    private int score;

    private Boolean status = false;

    private Boolean allClear = false;

    public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus() {
        this.status = true;
    }

    public Boolean getAllClear() {
        return allClear;
    }

    public void setAllClear(Boolean allClear) {
        this.allClear = allClear;
        this.status = false;
    }


    public ObjectOutputStream getOOS() {
        return oos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    Thread listenForDraw = new Thread(new Runnable() {
        String received = "";
        
        @Override
        public void run() {
            while (true) {
                setAllClear(false);

                while(true) {
                    try {
    
                        received = (String) ois.readObject();

                        if (received.equals("draw"))
                            oos.writeObject(GamePlay.gameDeck.getDeck().remove(0));
                        else
                            break;

                    } catch(Exception e) {e.printStackTrace();}
                }

                try {
                    setScore((int) ois.readObject());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                setStatus();

                while(!getAllClear()) {
                    
                    try {
                     TimeUnit.SECONDS.sleep(1);
                    } catch(Exception e) { e.printStackTrace(); }
                    

                }
            }
        }
    });

    @Override
    public void run() {
        try {
            setName((String) ois.readObject());
            System.out.println(getName() + " is ready to play!");

            listenForDraw.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(true) {}

    }   

}

class GamePlay {

    public static Hand dealersHand = new Hand();

    public static Deck gameDeck = new Deck();

    public static void setup() {
        gameDeck.setDeck();
        gameDeck.shuffle();

        for (ClientHandler CL : BlackJackServer.getClientHandlers()) {
            try {
                CL.getOOS().writeObject((Card) gameDeck.getDeck().remove(0));
                CL.getOOS().writeObject((Card) gameDeck.getDeck().remove(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        dealersHand.clearHand();
        dealersHand.addToHand(gameDeck.getDeck().remove(0));
        dealersHand.addToHand(gameDeck.getDeck().remove(0));
    }

    public static Hand dealerPlay(Hand dealer, Deck gameDeck) {

        while (dealer.countHand() < 17 && dealer.countHand() != 0) {
            dealer.addToHand(gameDeck.getDeck().remove(0));
        }
        System.out.println("Dealer has " + dealer.countHand());
        return dealer;
    }

    public static void playGame() {
        Boolean playerLive = true;

        while (true) {
            playerLive = true;

            while (playerLive) {
                playerLive = false;

                for(ClientHandler client : BlackJackServer.getClientHandlers()) {
                        if (!client.getStatus()) {
                            playerLive = true;
                            break;
                        }
                   
                }
            }

            dealerPlay(dealersHand, gameDeck);
            System.out.println(pickWinner());

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch(Exception e) { e.printStackTrace(); }

            for(ClientHandler client : BlackJackServer.getClientHandlers()) {
                try {
                    client.getOOS().writeObject(pickWinner());
                    client.setAllClear(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(Exception e) { e.printStackTrace(); }

            setup();


        }
        

        
    }

    public static String pickWinner() {
        int winningScore = 1;
        ArrayList<ClientHandler> winners = new ArrayList<ClientHandler>();
        String output = "Winner(s): ";

        for (ClientHandler CL : BlackJackServer.addressBook) {

            if(CL.getScore() > winningScore) {
                winningScore = CL.getScore();

                winners.clear();
                winners.add(CL);
            }
            else if (CL.getScore() == winningScore) {
                winners.add(CL);
            }

        }
        if(dealersHand.countHand() > winningScore)
            return "Dealer Wins with " + dealersHand.countHand();
        else if(winningScore <= 1)
            return "EVERYONE BUST!";
        else {
            for (ClientHandler player : winners) {
                output += player.getName() + " ";
            }
            return output + "with a total of " + winningScore;
        }

    }
}