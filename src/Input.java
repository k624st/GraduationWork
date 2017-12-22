import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Input extends JFrame implements ActionListener{
	
	public static void main(String[] args){
		System.out.println("スタート");
		new Input();
		System.out.println("エンド");
	}  
  
	JLabel label;
	Button button;
	Button btnWait;
	JTextField text;
	
	MyThread3 t1 = new MyThread3();
	  
	public Input() {
		JPanel jPanel1 = new JPanel();
		JPanel jPanel2 = new JPanel();
		
		label = new JLabel("駅名を入力してください。");
		text = new JTextField(10);
		button = new Button("決定");
		button.addActionListener(this);
		btnWait = new Button("一時停止");
		btnWait.addActionListener(this);
		
		this.setTitle("駅名入力フォーム");
		this.setBounds(100, 100, 300, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		jPanel1.add(label);
		jPanel2.add(text);
		jPanel2.add(button);
		jPanel2.add(btnWait);
		
		Container contentPane = getContentPane();
		contentPane.add(jPanel1, BorderLayout.NORTH);
		contentPane.add(jPanel2, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button){
			t1.start();
		}else if (e.getSource() == btnWait){
			t1.setStop();
		}
	}   
}
  
class MyThread3 extends Thread {
	boolean stop = true;
	
	public void run() {
		while(!stop){
			try {
				synchronized(this){
					if (stop) {
						wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void setStop() {
		stop = !stop;
		if (!stop) {
			notify();
		}
	}
}