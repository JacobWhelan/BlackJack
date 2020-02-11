// class containing client code to connect to the server and play the game

import java.io.*; 
import java.net.*; 
import java.util.Scanner;

public class Client {
    
    private static Hand myHand;

    private static String name;

    private static Scanner input = new Scanner(System.in); 

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    private static Socket socket;

    public static GUI myUI;

    public static String getName() {
        return name;
    }

    public static void setName(String name1) {
        name = name1;
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    public static void disconnect() {
        try {
            socket.close();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public static void setup() {
        try {
            InetAddress localhost = InetAddress.getByName("localhost");

            socket = new Socket(localhost, 5069);

            myHand = new Hand();
            
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            // setting username/making local player object
            System.out.println("What's your name?:");
            setName(input.nextLine());
 
            oos.writeObject(getName());

            myUI = new GUI(ois);

            myUI.showGUI();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setupHand() {
        myHand.clearHand();

        try {
            myHand.addToHand((Card) ois.readObject());
            myHand.addToHand((Card) ois.readObject());
        } catch(Exception e) { 
            e.printStackTrace();
            disconnect();
        }

        myUI.updateHand(myHand);
        //clearScreen();

        

        System.out.println("NEW HAND!");
        System.out.println(myHand + " Draw/Stick?");
    }


    public static void drawCard() {
        
        try {
            oos.writeObject("draw");
            myHand.addToHand((Card) ois.readObject());

            System.out.println("hey");

            myUI.updateHand(myHand);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void endHand() {
        try {
            oos.writeObject("stick");
            oos.writeObject((int) myHand.countHand());
            System.out.println(myHand + " Total: " + myHand.countHand());

            myUI.printResults((String) ois.readObject());

            // Print result
            //System.out.println(ois.readObject());
            setupHand();
        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }


    public static void main(String[] Ari) { 
        /*
        Thread liveHand = new Thread(new Runnable(){
        
            @Override
            public void run() {
                setupHand();

            }
        });
        */
        setup();
        setupHand();

        while(true) {}

    }


}