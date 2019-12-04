package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class Application extends JFrame implements WindowListener,ActionListener,KeyListener{
	JTextArea outputStr = new JTextArea("Altiorem science 2019\n");
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenuItem clrScr = new JMenuItem("Clear screen");
	JMenuItem inStr = new JMenuItem("Input text");
	JTextArea inputStr = new JTextArea("");
	JButton enterBtn = new JButton(">");
	JPanel panel = new JPanel();
	JScrollPane scrollPane = new JScrollPane(outputStr);
	String text="Altiorem science 2019\n",Intext="",returnString="";
	boolean enterKey=false;
	public Application(){
		initUI();
	}
	void initUI(){
        addWindowListener(this);
        setTitle("Console");
        setSize(512,512);
        setResizable(false);
        setLocationRelativeTo(null);
        outputStr.setSelectedTextColor(new Color(0,127,255));
        outputStr.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        outputStr.setEditable(false);
        outputStr.setSize(506, 430);
        scrollPane.setSize(507, 431);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputStr.setBorder(BorderFactory.createBevelBorder(1));
        inputStr.setSelectedTextColor(new Color(0,127,255));
        inputStr.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        inputStr.setBounds(0, 430, 446, 30);
        inputStr.addKeyListener(this);
        enterBtn.setBounds(446, 430, 60, 30);
        enterBtn.setToolTipText("Press to enter text");
        clrScr.setMnemonic(KeyEvent.VK_F);
        clrScr.setToolTipText("Clears all output chars");
        clrScr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                clearScreen();
            }
        });
        enterBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                writeln(inputStr.getText());
                enterKey=true;
            }
        });
        clrScr.setMnemonic(KeyEvent.VK_C);
        clrScr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        inStr.setMnemonic(KeyEvent.VK_I);
        inStr.setToolTipText("Inputs text from line to system");
        inStr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                writeln(inputStr.getText());
                enterKey=true;
            }
        });
        inStr.setMnemonic(KeyEvent.VK_ALT);
        inStr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ALT, 0));
        file.add(clrScr);
        file.add(inStr);
        menuBar.add(file);
        setJMenuBar(menuBar);
        add(scrollPane);
        add(inputStr);
        add(enterBtn);
        add(panel);
        setVisible(true);
	}
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ALT) {
            writeln(inputStr.getText());
            enterKey=true;
        }
    }
    public void clearScreen(){
        outputStr.setText("");
        text="";
    }
    @Override
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }
    public String get(String str){
        enterKey=false;
        returnString="";
        while(true){
            try {
                if(enterKey){
                    enterKey=false;
                    returnString=inputStr.getText();
                    inputStr.setText("");
                    Thread.sleep(100);
                    return returnString;
                }
            Thread.sleep(17);
            }catch(InterruptedException e){}
        }
    }
    public int get(int str){
        enterKey=false;
        returnString="";
        while(true){
            try {
                if(enterKey){
                    returnString=inputStr.getText();
                    str=Integer.parseInt(returnString);
                    enterKey=false;
                    inputStr.setText("");
                    Thread.sleep(100);
                    return str;
                }
            Thread.sleep(17);
            }catch(InterruptedException e){}
        }
    }
    public double get(double str){
        enterKey=false;
        returnString="";
        while(true){
            try {
                if(enterKey){
                    returnString=inputStr.getText();
                    str=Double.parseDouble(returnString);
                    enterKey=false;
                    inputStr.setText("");
                    Thread.sleep(100);
                    return str;
                }
            Thread.sleep(17);
            }catch(InterruptedException e){}
        }
    }
    public boolean get(boolean str){
        enterKey=false;
        returnString="";
        while(true){
            try {
                if(enterKey){
                        returnString=inputStr.getText();
                        str=Boolean.parseBoolean(returnString);
                        enterKey=false;
                        inputStr.setText("");
                        Thread.sleep(100);
                        return str;
                }
            Thread.sleep(17);
            }catch(InterruptedException e){}
        }
    }
    public char get(char str){
        enterKey=false;
        returnString="";
        while(true){
            try {
                if(enterKey){
                    returnString=inputStr.getText();
                    str=returnString.charAt(0);
                    enterKey=false;
                    inputStr.setText("");
                    Thread.sleep(100);
                    return str;
                }
            Thread.sleep(17);
            }catch(InterruptedException e){}
        }
    }
    public void write(String str){
        outputStr.setText(text + str);
        text+=str;
    }
    public void write(int str1){
        String str;
        str = Integer.toString(str1);
        outputStr.setText(text + str);
        text+=str;
    }
    public void write(double str1){
        String str;
        str = Double.toString(str1);
        outputStr.setText(text + str);
        text+=str;
    }
    public void write(boolean str1){
        String str;
        str = Boolean.toString(str1);
        outputStr.setText(text + str);
        text+=str;
    }
    public void write(char str){
        outputStr.setText(text + str);
        text+=str;
    }
    public void writeln(String str){
        outputStr.setText(text+"\n"+str);
        text=text+"\n"+str;
    }
    public void writeln(int str1){
        String str;
        str = Integer.toString(str1);
        outputStr.setText(text+"\n"+str);
        text=text+"\n"+str;
    }
    public void writeln(double str1){
        String str;
        str = Double.toString(str1);
        outputStr.setText(text+"\n"+str);
        text=text+"\n"+str;
    }
    public void writeln(boolean str1){
        String str;
        str = Boolean.toString(str1);
        outputStr.setText(text+"\n"+str);
        text=text+"\n"+str;
    }
    public void writeln(char str){
        outputStr.setText(text+"\n"+str);
        text=text+"\n"+str;
    }
    @Override
    public void keyReleased(KeyEvent e){  
    }  
    @Override
    public void keyTyped(KeyEvent e){  
    }  
    @Override
    public void actionPerformed(ActionEvent ae) { 	
    }
    @Override
    public void windowOpened(WindowEvent we) {
    }
    @Override
    public void windowClosed(WindowEvent we) {
    }
    @Override
    public void windowIconified(WindowEvent we) {
    }
    @Override
    public void windowDeiconified(WindowEvent we) {
    }
    @Override
    public void windowActivated(WindowEvent we) {
    }
    @Override
    public void windowDeactivated(WindowEvent we) {
    }
}