
/*
A basic extension of the com.sun.java.swing.JApplet class
*/

import com.sun.java.swing.*;
import java.awt.*;

public class MarkovChainApplet extends JApplet implements Runnable
{
    Thread t;
	private Image offScreenImage;
	private Graphics offScreenGraphics;  
	int width, height;
	World ThisWorld;
    
	public void init()
	{
		Dimension r = size();
		width = r.width;
		height = 200;

		
		
		// Take out this line if you don't use symantec.itools.net.RelativeURL or symantec.itools.awt.util.StatusScroller
		symantec.itools.lang.Context.setApplet(this);
		
		// This line prevents the "Swing: checked access to system event queue" message seen in some browsers.
		getRootPane().putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
		
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		getContentPane().setLayout(null);
		getContentPane().setBackground(java.awt.Color.white);
		setSize(450,367);
		horizontalScrollbarRedSpeed.setBlockIncrement(4);
		getContentPane().add(horizontalScrollbarRedSpeed);
		horizontalScrollbarRedSpeed.setBounds(96,264,156,12);
		getContentPane().add(horizontalScrollbarRedCoh);
		horizontalScrollbarRedCoh.setBounds(96,288,156,12);
		getContentPane().add(horizontalScrollbarRedAvoid);
		horizontalScrollbarRedAvoid.setBounds(96,312,156,12);
		getContentPane().add(horizontalScrollbarRedInter);
		horizontalScrollbarRedInter.setBounds(96,336,156,12);
		horizontalScrollbarBlueSpeed.setBlockIncrement(4);
		getContentPane().add(horizontalScrollbarBlueSpeed);
		horizontalScrollbarBlueSpeed.setBounds(264,264,156,12);
		getContentPane().add(horizontalScrollbarBlueCoh);
		horizontalScrollbarBlueCoh.setBounds(264,288,156,12);
		getContentPane().add(horizontalScrollbarBlueAvoid);
		horizontalScrollbarBlueAvoid.setBounds(264,312,156,12);
		getContentPane().add(horizontalScrollbarBlueInter);
		horizontalScrollbarBlueInter.setBounds(264,336,156,12);
		labelRedSpecies.setText("Red Species");
		getContentPane().add(labelRedSpecies);
		labelRedSpecies.setForeground(java.awt.Color.red);
		labelRedSpecies.setFont(new Font("Dialog", Font.BOLD, 12));
		labelRedSpecies.setBounds(144,216,72,12);
		labelBlueSpecies.setText("Blue Species");
		getContentPane().add(labelBlueSpecies);
		labelBlueSpecies.setForeground(java.awt.Color.blue);
		labelBlueSpecies.setFont(new Font("Dialog", Font.BOLD, 12));
		labelBlueSpecies.setBounds(300,216,72,12);
		labelInteraction.setText("Interaction");
		labelInteraction.setAlignment(java.awt.Label.RIGHT);
		labelInteraction.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		getContentPane().add(labelInteraction);
		labelInteraction.setFont(new Font("Dialog", Font.BOLD, 12));
		labelInteraction.setBounds(12,336,72,12);
		labelAvoidance.setText("Avoidance");
		labelAvoidance.setAlignment(java.awt.Label.RIGHT);
		getContentPane().add(labelAvoidance);
		labelAvoidance.setFont(new Font("Dialog", Font.BOLD, 12));
		labelAvoidance.setBounds(12,312,72,12);
		labelCohesion.setText("Cohesion");
		labelCohesion.setAlignment(java.awt.Label.RIGHT);
		getContentPane().add(labelCohesion);
		labelCohesion.setFont(new Font("Dialog", Font.BOLD, 12));
		labelCohesion.setBounds(12,288,72,12);
		labelSpeed.setText("Speed");
		labelSpeed.setAlignment(java.awt.Label.RIGHT);
		labelSpeed.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		getContentPane().add(labelSpeed);
		labelSpeed.setFont(new Font("Dialog", Font.BOLD, 12));
		labelSpeed.setBounds(12,264,72,12);
		getContentPane().add(horizontalScrollbarRedAmount);
		horizontalScrollbarRedAmount.setBounds(120,240,132,12);
		labelAmount.setText("Amount");
		labelAmount.setAlignment(java.awt.Label.RIGHT);
		labelAmount.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		getContentPane().add(labelAmount);
		labelAmount.setFont(new Font("Dialog", Font.BOLD, 12));
		labelAmount.setBounds(12,240,72,12);
		getContentPane().add(horizontalScrollbarBlueAmount);
		horizontalScrollbarBlueAmount.setBounds(288,240,132,12);
		textFieldRedAmount.setText("10");
		getContentPane().add(textFieldRedAmount);
		textFieldRedAmount.setBounds(96,238,24,16);
		textFieldBlueAmount.setText("20");
		getContentPane().add(textFieldBlueAmount);
		textFieldBlueAmount.setBounds(264,238,24,16);
		//}}
	
		//{{REGISTER_LISTENERS
		SymMouse aSymMouse = new SymMouse();
		labelSpeed.addMouseListener(aSymMouse);
		labelInteraction.addMouseListener(aSymMouse);
		labelAvoidance.addMouseListener(aSymMouse);
		SymText lSymText = new SymText();
		textFieldRedAmount.addTextListener(lSymText);
		SymAdjustment lSymAdjustment = new SymAdjustment();
		horizontalScrollbarRedAmount.addAdjustmentListener(lSymAdjustment);
		textFieldBlueAmount.addTextListener(lSymText);
		horizontalScrollbarBlueAmount.addAdjustmentListener(lSymAdjustment);
		SymFocus aSymFocus = new SymFocus();
		textFieldRedAmount.addFocusListener(aSymFocus);
		textFieldBlueAmount.addFocusListener(aSymFocus);
		horizontalScrollbarRedSpeed.addAdjustmentListener(lSymAdjustment);
		horizontalScrollbarBlueSpeed.addAdjustmentListener(lSymAdjustment);
		horizontalScrollbarRedCoh.addAdjustmentListener(lSymAdjustment);
		horizontalScrollbarBlueCoh.addAdjustmentListener(lSymAdjustment);
		horizontalScrollbarRedAvoid.addAdjustmentListener(lSymAdjustment);
		horizontalScrollbarBlueAvoid.addAdjustmentListener(lSymAdjustment);
		horizontalScrollbarRedInter.addAdjustmentListener(lSymAdjustment);
		horizontalScrollbarBlueInter.addAdjustmentListener(lSymAdjustment);
		//}}
		
	    ThisWorld = new World();
		offScreenImage = createImage(r.width, r.height);
		offScreenGraphics = offScreenImage.getGraphics();
		repaint();
		
	}

	//{{DECLARE_CONTROLS
	java.awt.Scrollbar horizontalScrollbarRedSpeed = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,20,2,0,22);
	java.awt.Scrollbar horizontalScrollbarRedCoh = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,100,5,50,105);
	java.awt.Scrollbar horizontalScrollbarRedAvoid = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,100,5,50,105);
	java.awt.Scrollbar horizontalScrollbarRedInter = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,100,10,0,110);
	java.awt.Scrollbar horizontalScrollbarBlueSpeed = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,20,2,0,22);
	java.awt.Scrollbar horizontalScrollbarBlueCoh = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,100,5,50,105);
	java.awt.Scrollbar horizontalScrollbarBlueAvoid = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,100,5,50,105);
	java.awt.Scrollbar horizontalScrollbarBlueInter = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,0,10,0,110);
	java.awt.Label labelRedSpecies = new java.awt.Label();
	java.awt.Label labelBlueSpecies = new java.awt.Label();
	java.awt.Label labelInteraction = new java.awt.Label();
	java.awt.Label labelAvoidance = new java.awt.Label();
	java.awt.Label labelCohesion = new java.awt.Label();
	java.awt.Label labelSpeed = new java.awt.Label();
	java.awt.Scrollbar horizontalScrollbarRedAmount = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,10,10,0,109);
	java.awt.Label labelAmount = new java.awt.Label();
	java.awt.Scrollbar horizontalScrollbarBlueAmount = new java.awt.Scrollbar(Scrollbar.HORIZONTAL,20,10,0,109);
	java.awt.TextField textFieldRedAmount = new java.awt.TextField();
	java.awt.TextField textFieldBlueAmount = new java.awt.TextField();
	//}}

	class SymMouse extends java.awt.event.MouseAdapter
	{
		public void mouseExited(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == labelInteraction)
				labelInteraction_MouseExited(event);
		}

		public void mouseEntered(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == labelInteraction)
				labelInteraction_MouseEntered(event);
		}
	}

	class SymText implements java.awt.event.TextListener
	{
		public void textValueChanged(java.awt.event.TextEvent event)
		{
			Object object = event.getSource();
			if (object == textFieldRedAmount)
				textFieldRedAmount_TextValueChanged(event);
			else if (object == textFieldBlueAmount)
				textFieldBlueAmount_TextValueChanged(event);
		}
	}

	void textFieldRedAmount_TextValueChanged(java.awt.event.TextEvent event)
	{
		// to do: code goes here.
			 
		textFieldRedAmount_TextValueChanged_Interaction1(event);
	}

	void textFieldRedAmount_TextValueChanged_Interaction1(java.awt.event.TextEvent event)
	{
		try {
			// horizontalScrollbarRedAmount Set the Scrollbar's value; textFieldRedAmount Get the contents as an int
			horizontalScrollbarRedAmount.setValue(Integer.parseInt(textFieldRedAmount.getText()));
		} catch (Exception e) {
		}
	}

	class SymAdjustment implements java.awt.event.AdjustmentListener
	{
		public void adjustmentValueChanged(java.awt.event.AdjustmentEvent event)
		{
			Object object = event.getSource();
			if (object == horizontalScrollbarRedAmount)
				horizontalScrollbarRedAmount_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarBlueAmount)
				horizontalScrollbarBlueAmount_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarRedSpeed)
				horizontalScrollbarRedSpeed_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarBlueSpeed)
				horizontalScrollbarBlueSpeed_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarRedCoh)
				horizontalScrollbarRedCoh_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarBlueCoh)
				horizontalScrollbarBlueCoh_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarRedAvoid)
				horizontalScrollbarRedAvoid_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarBlueAvoid)
				horizontalScrollbarBlueAvoid_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarRedInter)
				horizontalScrollbarRedInter_AdjustmentValueChanged(event);
			else if (object == horizontalScrollbarBlueInter)
				horizontalScrollbarBlueInter_AdjustmentValueChanged(event);
		}
	}

	void horizontalScrollbarRedAmount_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		// to do: code goes here.
			 
		horizontalScrollbarRedAmount_AdjustmentValueChanged_Interaction1(event);
	}

	void horizontalScrollbarRedAmount_AdjustmentValueChanged_Interaction1(java.awt.event.AdjustmentEvent event)
	{
		try {
			// textFieldRedAmount Set the text for TextField; convert int->class java.lang.String; horizontalScrollbarRedAmount Get the Scrollbar's value
			textFieldRedAmount.setText(java.lang.String.valueOf(horizontalScrollbarRedAmount.getValue()));
            ThisWorld.changeRedAmount(horizontalScrollbarRedAmount.getValue());
		} catch (Exception e) {
		}
	}

	void textFieldBlueAmount_TextValueChanged(java.awt.event.TextEvent event)
	{
		// to do: code goes here.
			 
		textFieldBlueAmount_TextValueChanged_Interaction1(event);
	}

	void textFieldBlueAmount_TextValueChanged_Interaction1(java.awt.event.TextEvent event)
	{
		try {
			// horizontalScrollbarBlueAmount Set the Scrollbar's value; textFieldBlueAmount Get the contents as an int
			horizontalScrollbarBlueAmount.setValue(Integer.parseInt(textFieldBlueAmount.getText()));
            ThisWorld.changeBlueAmount(horizontalScrollbarBlueAmount.getValue());
		} catch (Exception e) {
		}
	}

	void horizontalScrollbarBlueAmount_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		// to do: code goes here.
			 
		horizontalScrollbarBlueAmount_AdjustmentValueChanged_Interaction1(event);
	}

	void horizontalScrollbarBlueAmount_AdjustmentValueChanged_Interaction1(java.awt.event.AdjustmentEvent event)
	{
		try {
			// textFieldBlueAmount Set the text for TextField; convert int->class java.lang.String; horizontalScrollbarBlueAmount Get the Scrollbar's value
			textFieldBlueAmount.setText(java.lang.String.valueOf(horizontalScrollbarBlueAmount.getValue()));
		} catch (Exception e) {
		}
	}

	void labelInteraction_MouseEntered(java.awt.event.MouseEvent event)
	{
		// to do: code goes here.
			 
		labelInteraction_MouseEntered_Interaction1(event);
	}

	void labelInteraction_MouseEntered_Interaction1(java.awt.event.MouseEvent event)
	{
		try {
			// labelInteraction Set the foreground color; labelInteraction The color blue
			labelInteraction.setForeground(Color.blue);
			labelInteraction.repaint();
		} catch (Exception e) {
		}
	}

	void labelInteraction_MouseExited(java.awt.event.MouseEvent event)
	{
		// to do: code goes here.
			 
		labelInteraction_MouseExited_Interaction1(event);
	}

	void labelInteraction_MouseExited_Interaction1(java.awt.event.MouseEvent event)
	{
		try {
			// labelInteraction Set the foreground color; labelInteraction The color black
			labelInteraction.setForeground(Color.black);
			labelInteraction.repaint();
		} catch (Exception e) {
		}
	}

	class SymFocus extends java.awt.event.FocusAdapter
	{
		public void focusLost(java.awt.event.FocusEvent event)
		{
			Object object = event.getSource();
			if (object == textFieldRedAmount)
				textFieldRedAmount_FocusLost(event);
			else if (object == textFieldBlueAmount)
				textFieldBlueAmount_FocusLost(event);
		}
	}

	void textFieldRedAmount_FocusLost(java.awt.event.FocusEvent event)
	{
		// to do: code goes here.
			 
		textFieldRedAmount_FocusLost_Interaction1(event);
	}

	void textFieldRedAmount_FocusLost_Interaction1(java.awt.event.FocusEvent event)
	{
		try {
			// textFieldRedAmount Set the text for TextField; convert int->class java.lang.String; horizontalScrollbarRedAmount Get the Scrollbar's value
			textFieldRedAmount.setText(java.lang.String.valueOf(horizontalScrollbarRedAmount.getValue()));
		} catch (Exception e) {
		}
	}

	void textFieldBlueAmount_FocusLost(java.awt.event.FocusEvent event)
	{
		// to do: code goes here.
			 
		textFieldBlueAmount_FocusLost_Interaction1(event);
	}

	void textFieldBlueAmount_FocusLost_Interaction1(java.awt.event.FocusEvent event)
	{
		try {
			// textFieldBlueAmount Set the text for TextField; convert int->class java.lang.String; horizontalScrollbarBlueAmount Get the Scrollbar's value
			textFieldBlueAmount.setText(java.lang.String.valueOf(horizontalScrollbarBlueAmount.getValue()));
		} catch (Exception e) {
		}
	}

	

	
	public void start(){
		if( t == null ){
			t = new Thread(this);
			t.start();
		}
	}
	public void stop (){
		if( t != null ){
			t.stop();
			t=null;
		}
	}
	public void destroy (){
		this.stop();
	}
	public void run(){
		while(t != null){
			ThisWorld.step();
			repaint();
			try{Thread.sleep(100);
			}
			catch(InterruptedException e){}
		}
	}
	
	public boolean mouseUp(Event evt,int x,int y){
		clear();
		reset();
		repaint();
		return true;
	}
	public void reset(){
	}
	public void clear(){
		offScreenGraphics.setColor(new Color(0,0,0));
		offScreenGraphics.fillRect(0,0,width,height);
	}
	
	public final synchronized void update (Graphics g)
	{
		clear();
		paint(offScreenGraphics);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public void paint(Graphics g){
	 
	    
        //while(true) {
    	    g.setColor(Color.red);

	        for(int i = 1; i <= ThisWorld.RedAmount; i++){
	            g.drawLine(ThisWorld.Red[i].getX(), ThisWorld.Red[i].getY(),
	                        ThisWorld.Red[i].getOldX(), ThisWorld.Red[i].getOldY());
	        
	        }
	
	        g.setColor(Color.blue);
    	    for(int i = 1; i <= ThisWorld.BlueAmount; i++){
    	        g.drawLine(ThisWorld.Blue[i].getX(), ThisWorld.Blue[i].getY(),
    	                    ThisWorld.Blue[i].getOldX(), ThisWorld.Blue[i].getOldY());
	        
    	    }
	    	    
	    //}
	    
	}

	void horizontalScrollbarRedSpeed_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		ThisWorld.RedSpeed = horizontalScrollbarRedSpeed.getValue();
			 
	}

	void horizontalScrollbarBlueSpeed_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		ThisWorld.BlueSpeed = horizontalScrollbarBlueSpeed.getValue();
			 
	}

	void horizontalScrollbarRedCoh_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		ThisWorld.RedCohesion[0][0] = horizontalScrollbarRedCoh.getValue()/100;
		ThisWorld.RedCohesion[0][1] = 1.0 - (horizontalScrollbarRedCoh.getValue()/100);
			 
	}

	void horizontalScrollbarBlueCoh_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		ThisWorld.BlueCohesion[0][0] = horizontalScrollbarBlueCoh.getValue()/100;
		ThisWorld.BlueCohesion[0][1] = 1.0 - (horizontalScrollbarBlueCoh.getValue()/100);
			 
	}

	void horizontalScrollbarRedAvoid_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		ThisWorld.RedAvoidance[0][1] = horizontalScrollbarRedAvoid.getValue()/100;
		ThisWorld.RedAvoidance[0][0] = 1.0 - (horizontalScrollbarRedAvoid.getValue()/100);
			 
	}

	void horizontalScrollbarBlueAvoid_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		ThisWorld.BlueAvoidance[0][1] = horizontalScrollbarBlueAvoid.getValue()/100;
		ThisWorld.BlueAvoidance[0][0] = 1.0 - (horizontalScrollbarBlueAvoid.getValue()/100);
			 
	}

	void horizontalScrollbarRedInter_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		ThisWorld.RedInteraction[0][0] = horizontalScrollbarRedInter.getValue()/100;
		ThisWorld.RedInteraction[0][1] = 1.0 - (horizontalScrollbarRedInter.getValue()/100);
			 
	}

	void horizontalScrollbarBlueInter_AdjustmentValueChanged(java.awt.event.AdjustmentEvent event)
	{
		ThisWorld.BlueInteraction[0][0] = horizontalScrollbarBlueInter.getValue()/100;
		ThisWorld.BlueInteraction[0][1] = 1.0 - (horizontalScrollbarBlueInter.getValue()/100);
			 
	}
}

