import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.*;

public class GUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    
    private Font buttonFont = new Font(Font.MONOSPACED,  Font.BOLD, 25);
    
    public static JTextField results;

    private static JPanel p = new JPanel();

    public GUI() {

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
        results.setBounds(0, 200, 400, 80);
        results.setFont(new Font(Font.MONOSPACED,  Font.PLAIN, 20));
        
        p.add(new JTextArea());
        p.setBounds(400, 0, 600, 350);

       
        // add everything
        add(results);
        add(draw);
        add(stick);
        add(p);

    }

    public void updateHand(Hand h) {
        BufferedImage img;
        JLabel j;

        p.removeAll();

        for(Card card : h.getHand()){
            img = card.getImage();
            j = new JLabel(new ImageIcon(img.getScaledInstance(100, 150, Image.SCALE_DEFAULT)));
            p.add(j);
        }

        repaint();
        revalidate();

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


    @Override
    public void actionPerformed(ActionEvent e) {
        String request = e.getActionCommand();

        if(request.equals("DRAW")) {
            Client.drawCard();
            
        }
        else if(request.equals("STICK"))
            Client.endHand();
        else 
            System.out.println("ERROR");
        
    }

}