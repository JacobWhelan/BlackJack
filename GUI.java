import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.ObjectInputStream;

public class GUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    
    private Font buttonFont = new Font(Font.MONOSPACED,  Font.BOLD, 25);

    public static DefaultListModel<Card> model;
    
    public static JTextField results;

    private ObjectInputStream ois;

    public GUI(ObjectInputStream ois) {
        this.ois = ois;

        JButton draw = new JButton("DRAW");
              draw.setBounds(0, 0, 200, 200);
              draw.setFont(buttonFont);
              draw.addActionListener(this);

        JButton stick = new JButton("STICK");
            stick.setBounds(200, 0, 200, 200);
            stick.setFont(buttonFont);
            stick.addActionListener(this);


        // results tab
        results = new JTextField();
        results.setBounds(50, 200, 250, 100);
        results.setFont(new Font(Font.MONOSPACED,  Font.PLAIN, 15));
        
        // make the card list
        JPanel handView = new JPanel();
        model = new DefaultListModel<>();
        JList<Card> handList = new JList<Card>(model);

        handView.add(handList);
        handView.setBounds(500, 0, 100, 100);


        // add everything
        add(results);
        add(draw);
        add(stick);
        add(handView);

    }

    public void showGUI() {
        setSize(1000, 500);
        setLayout(null);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void printResults(String result) {
        results.setText(result);
        revalidate();
    }

    public void updateHand(Hand hand) {
        model.clear();
        for(Card card : hand.getHand())
            model.addElement(card);

        revalidate();
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        String request = e.getActionCommand();

        if(request.equals("DRAW")) {
            Client.drawCard();
            System.out.println("hi");
        }
        else if(request.equals("STICK"))
            Client.endHand();
        else 
            System.out.println("ERROR");
        
    }

}