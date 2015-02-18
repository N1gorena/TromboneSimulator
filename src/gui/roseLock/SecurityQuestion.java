package gui.roseLock;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.ComboBoxUI;

public class SecurityQuestion extends JPanel {
	private AnswerLine answerLine;
	private JLabel question;
	private RoseLock myLock;
	
	public SecurityQuestion(String Q, String[] answers, RoseLock lock) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.BLACK);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		this.question = new JLabel(formatQuestion(Q));
		this.question.setForeground(Color.RED);
		this.question.setFont(this.question.getFont().deriveFont(13.0f));
		this.question.setAlignmentX(LEFT_ALIGNMENT);
		this.question.setAlignmentY(CENTER_ALIGNMENT);
		this.add(this.question);
		
		this.add(Box.createVerticalGlue());
		
		JButton answerButton = new JButton("Submit");
		JComboBox<String> answerComboBox = new JComboBox<String>();
		this.answerLine = new AnswerLine(answerComboBox,answerButton,answers, lock);
		this.answerLine.setSize(200, 10);
		this.answerLine.setAlignmentX(LEFT_ALIGNMENT);
		this.answerLine.setAlignmentY(CENTER_ALIGNMENT);
		this.add(answerLine);
	}
	
	
	private class AnswerLine extends JPanel implements ActionListener{
		private JButton submitButton;
		private JComboBox<String> answerBox;
		private RoseLock myLock;
		
		public AnswerLine(JComboBox<String> answers, JButton submit, String[] givenAnswers, RoseLock lock){
			this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
			this.setBackground(Color.BLACK);
			
			
			this.answerBox = answers;
			for(String s : givenAnswers){
				answerBox.addItem(s);
			}
			answerBox.setBackground(Color.BLACK);
			answerBox.setForeground(Color.WHITE);
			this.add(answerBox);
			
			this.submitButton = submit;
			submitButton.addActionListener(this);
			submitButton.setBackground(Color.BLACK);
			submitButton.setForeground(Color.BLUE);
			this.add(submitButton);
			
			this.myLock = lock;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//Only one Button Attached
			String selectedAnswer = (String) this.answerBox.getSelectedItem();
			System.out.println(selectedAnswer);
			if(selectedAnswer == "Japanese"){
				this.myLock.unlock();
			}
		}
	}
	
	private String formatQuestion(String Q){
		return "<html>" + Q + "</html>";
	}
}
