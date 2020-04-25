import java.awt.Color;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.MouseInputListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter.HighlightPainter;

public class MainPanel extends JPanel implements MouseListener,ActionListener,KeyListener {
    final DefaultHighlightPainter painter = new DefaultHighlightPainter(Color.pink);
	char last='q';

	FileTree fileTree;
	JTabbedPane tabbedPane ;
	JPopupMenu menu ;
	 JFileChooser chooser;

	public ArrayList<JTextArea> txtAreas;
	public MainPanel()
	{
		setLayout(null);
		setBounds(0,0,MainFrame.width,MainFrame.height);
		
		txtAreas=new ArrayList<>();
		  menu= new JPopupMenu("OPTION");
		  JMenuItem item = new JMenuItem("Test1");
		   item.addActionListener(this);
		   
		

		

		tabbedPane= new JTabbedPane();
		tabbedPane.setBounds(200, 0, MainFrame.width-200, MainFrame.height*3/4);

		

		add(new OutputPanel());
		add(tabbedPane);
	}

	public void openMenu(int x,int y) {
		
		JFrame frame=new JFrame("Options");
		frame.setSize(OptionMenu.width, OptionMenu.height);
		frame.add(new OptionMenu(frame,this,x,y));
		frame.setLocation(y,x);
		frame.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		 if(e.getButton()== MouseEvent.BUTTON3) {
	          openMenu(e.getX(),e.getY());
	          }
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void addBreakPoint(int x,int y) {
		JTextArea txt=((JTextArea)(((JScrollPane)tabbedPane.getSelectedComponent()).getViewport().getView())) ;
		txt.setCaretPosition(txt.viewToModel(new Point(x,y)));
	    System.out.println(txt.getCaretPosition());
	    String str=txt.getText();
	    for(int index=txt.getCaretPosition();index>0;index--)
	    {
	    	if(str.charAt(index)=='\n') {
	    		String str1;
	    		if(str.charAt(index+4)=='*')
	    		{
	    			str1=str.substring(0,index+1)+str.substring(index+6,str.length());
	    		}
	    		else
	    		 str1=str.substring(0, index+1)+"*****"+str.substring(index+1,str.length());
	    		
	    		txt.setText(str1);
	    		repaint();
	    		txt.repaint();
	    		( (JScrollPane) tabbedPane.getSelectedComponent() ).getVerticalScrollBar().setValue(0);
	    		return;
	    	}
	    }		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		JTextArea textarea=null;
		for(int a=0;a<txtAreas.size();a++) {
			if(e.getSource()==txtAreas.get(a))
				textarea=txtAreas.get(a);
		}
		highlight(textarea);

		if(e.getKeyChar()==' ')
		{
		}
		if(last=='f'&&e.getKeyChar()=='f')
		{
			String word=JOptionPane.showInputDialog("Enter word");
			String text=textarea.getText();
			int begin=text.indexOf(word);
			if(begin>0)
			{
				textarea.setSelectionStart(begin);
				textarea.setSelectionEnd(word.length());
				textarea.requestFocus();
				repaint();
				textarea.repaint();
				
			}
			
		}
		last=e.getKeyChar();
		
	}
	public void highlight(JTextArea textarea)
	{
	    String text = textarea.getText();
	    Highlighter highlighter = textarea.getHighlighter();
	    highlighter.removeAllHighlights();
        highlight(textarea, "if", painter);
        highlight(textarea, "else", painter);
        highlight(textarea, "for", painter);
        highlight(textarea, "while", painter);
        highlight(textarea, "public", painter);
        highlight(textarea, "static", painter);
        highlight(textarea, "void", painter);
        highlight(textarea, "private", painter);
        highlight(textarea, "int", painter);
        highlight(textarea, "double", painter);
        highlight(textarea, "char", painter);

		
	}
	 public  void highlight(JTextArea textarea, String textToHighlight,
		      HighlightPainter painter) {
		 
		  String text = textarea.getText();
		    Highlighter highlighter = textarea.getHighlighter();

		    if (!textToHighlight.isEmpty()) {
		      Matcher m = compileWildcard(textToHighlight).matcher(text);
		      while (m.find()) {
		        try {
		          highlighter.addHighlight(m.start(), m.end(), painter);
		        } catch (BadLocationException e) {
		          throw new IllegalStateException(e); /* cannot happen */
		        }
		        //textarea.setCaretPosition(m.end());
		      }
		    }
		  }

	 public  Pattern compileWildcard(String wildcard) {
		    StringBuilder sb = new StringBuilder("\\b"); /* word boundary */
		    /* the following replaceAll is just for performance */
		    for (char c : wildcard.replaceAll("\\*+", "*").toCharArray()) {
		      if (c == '*') {
		        sb.append("\\S*"); /*- arbitrary non-space characters */
		      } else {
		        sb.append(Pattern.quote(String.valueOf(c)));
		      }
		    }
		    sb.append("\\b"); /* word boundary */
		    return Pattern.compile(sb.toString());
		  }

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void createFileTree() {
		// TODO Auto-generated method stub
		   chooser = new JFileChooser(); 
		    chooser.setDialogTitle("Select project directory");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);
		    //    
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		      System.out.println("getCurrentDirectory(): " 
		         +  chooser.getCurrentDirectory());
		      System.out.println("getSelectedFile() : " 
		         +  chooser.getSelectedFile());
		      }
		    else {
		      System.out.println("No Selection ");
		      }
		    fileTree=new FileTree(this,tabbedPane,chooser.getSelectedFile());
			fileTree.setBounds(0, 20, 200,500);
			add(fileTree);
			fileTree.repaint();
			repaint();
	}

	

	



}
