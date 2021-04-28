/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stopwatch2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

/**
 *
 * @author Ak
 */
public class Stopwatch2 extends JFrame{
    Timer autoDisplay;
    
    JLabel runningTimeLabel=new JLabel();
    JLabel totalTimeLabel=new JLabel();
    JTextField runningTimefield=new JTextField();
    JTextField totalTimefield=new JTextField();
    JButton startStopButton=new JButton();
    JButton resetButton=new JButton();
    JButton exitButton=new JButton();
    
    long startTime; long stoppedTime; long stopTime;
    long currentTime;
   
    public static void main(String[] args) {
        new Stopwatch2().show(); //Creates the frame.
    }
    
    public Stopwatch2(){
        //Constructor
        setTitle("Stopwatch 2.0");
        setResizable(false);
        
        addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e){
            exitForm(e);
        }});
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints=new GridBagConstraints();
        
        runningTimeLabel.setText("Running Time:");
        runningTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gridConstraints= new GridBagConstraints();
        gridConstraints.gridx=0;
        gridConstraints.gridy=0;
        gridConstraints.insets=new Insets(10,25,0,0);
        getContentPane().add(runningTimeLabel,gridConstraints);
        
        runningTimefield.setText("00:00:00");
        runningTimefield.setEditable(false);
        runningTimefield.setBackground(Color.white);
        runningTimefield.setForeground(Color.blue);
        runningTimefield.setPreferredSize(new Dimension(150,50));
        runningTimefield.setFont(new Font("Arial", Font.BOLD, 18));
        runningTimefield.setHorizontalAlignment(JTextField.CENTER);
        gridConstraints= new GridBagConstraints();
        gridConstraints.gridx=0; gridConstraints.gridy=1;
        gridConstraints.gridwidth=2;
        gridConstraints.insets=new Insets(0,10,0,10);
        getContentPane().add(runningTimefield,gridConstraints);
        
        totalTimeLabel.setText("Total Time:");
        totalTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gridConstraints.gridx=0; gridConstraints.gridy=2;
        gridConstraints.insets=new Insets(10,10,0,10);
        getContentPane().add(totalTimeLabel, gridConstraints);
        
        totalTimefield.setText("00:00:00");
        totalTimefield.setEditable(false);
        totalTimefield.setPreferredSize(new Dimension(150,50));
        totalTimefield.setFont(new Font("Arial", Font.BOLD,18));
        totalTimefield.setBackground(Color.white);
        totalTimefield.setForeground(Color.red);
        totalTimefield.setHorizontalAlignment(JTextField.CENTER);
        gridConstraints.gridx=0; gridConstraints.gridy=3;
        gridConstraints.gridwidth=2;
        gridConstraints.insets=new Insets(0,10,15,10);
        getContentPane().add(totalTimefield,gridConstraints);
        
        startStopButton.setText("Start");
        gridConstraints.gridx=0; gridConstraints.gridy=4;
        getContentPane().add(startStopButton, gridConstraints);
        
        resetButton.setText("Reset");
        resetButton.setEnabled(false);
        gridConstraints.gridx=1; gridConstraints.gridy=4;
        gridConstraints.insets= new Insets(0,10,0,25);
        getContentPane().add(resetButton, gridConstraints);
        
        exitButton.setText("Exit");
        gridConstraints.gridx=1; gridConstraints.gridy=5;
        gridConstraints.insets= new Insets(10,0,10,25);
        getContentPane().add(exitButton, gridConstraints);
        pack();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Sets the application to the screen center.
        setBounds((int)(0.5*(screenSize.width - getWidth())), (int) (0.5 *(screenSize.height-getHeight())), getWidth(), getHeight());
        
        startStopButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startStopAction(e);
            }
        });
        resetButton.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               resetAction(e);
           } 
        });
        exitButton.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               exitAction(e);
           } 
        });
        autoDisplay= new Timer(1000,new ActionListener(){
           public void actionPerformed(ActionEvent e){
               autoDisplayAction(e);
           } 
        });
    }
        
    private void exitForm(WindowEvent e){
        System.exit(0);
    }
    private void exitAction(ActionEvent e){
        System.exit(0);
    }
    private void startStopAction(ActionEvent e){
        if(startStopButton.getText().equals("Start")){
            startStopButton.setText("Stop");
            exitButton.setEnabled(false);
            startTime=System.currentTimeMillis();
            stoppedTime=0;
            autoDisplay.start();
        }else if(startStopButton.getText().equals("Stop")){
            stopTime=System.currentTimeMillis();
            startStopButton.setText("Restart");
            resetButton.setEnabled(true);
            exitButton.setEnabled(true);
            autoDisplay.stop();
            totalTimefield.setText(HMS(currentTime-startTime-stoppedTime));
        }else if(startStopButton.getText().equals("Restart")){
            stoppedTime+=System.currentTimeMillis()-stopTime;
            startStopButton.setText("Stop");
            resetButton.setEnabled(false);
            exitButton.setEnabled(false);
           totalTimefield.setText(HMS(currentTime-startTime));
            autoDisplay.start();
        }
        
    }
    private void resetAction(ActionEvent e){
        runningTimefield.setText("00.00.00");
        totalTimefield.setText("00.00.00");
        startStopButton.setText("Start");
        resetButton.setEnabled(false);
    }
    private String HMS(long tms){//Displays time in HrMinSec format
        int h,m,s; double t;
        t= tms/1000.0;
        h= (int)(t/3600);
        m= (int)((t-h*3600)/60); s=(int)(t-h*3600-m*60);
        return(new DecimalFormat("00").format(h)+":"+new DecimalFormat("00").format(m)+":"+new DecimalFormat("00").format(s));
    }
    private void autoDisplayAction(ActionEvent e){
        currentTime=System.currentTimeMillis();
        runningTimefield.setText(HMS(currentTime-startTime-stoppedTime));
    }
}
