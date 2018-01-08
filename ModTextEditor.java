package Editor;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class ModTextEditor extends WindowClass implements ActionListener, ItemListener,TextListener 
{
	Frame f;
	Dialog dg,dg_child;
	static TextArea ta;
	MenuBar mb;
	Menu file,edit,option,others,view;
	MenuItem neu,open,save,saveas,exit,cut,copy,paste,selectAll,two,setbackground,find,replace;
	CheckboxMenuItem bold,italic,statusbar;
	String temp;
	boolean change=false,windowclose=false,newrequested=false,incut=false;
	TextField tf,tf1; 
	Label l1,l2,l3,l4;
	Button yes,no,cancel,findnext,replace1,replaceall; 
	Panel p;
	int prev=-1;
	
	ModTextEditor()
	{
		f=new Frame();
		f.setSize(200,200);
		f.setLocation(100,100);
		
		ta=new TextArea();
		
		mb=new MenuBar();
		
		neu=new MenuItem("New");
		open=new MenuItem("Open");
		save=new MenuItem("Save");
		saveas=new MenuItem("Save As");
		exit=new MenuItem("Exit");
		copy=new MenuItem("Copy");
		cut=new MenuItem("Cut");
		paste=new MenuItem("Paste");
		find=new MenuItem("Find");
		replace=new MenuItem("Replace");
		selectAll=new MenuItem("Select All");
		two=new MenuItem("Two");
		setbackground=new MenuItem("Set Background");
		 
		
		neu.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		saveas.addActionListener(this);
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		find.addActionListener(this);
		replace.addActionListener(this);
		selectAll.addActionListener(this);
		two.addActionListener(this);
		exit.addActionListener(this);
		setbackground.addActionListener(this);
		paste.setEnabled(false);
		ta.addTextListener(this);
		
		file=new Menu("File");
		edit=new Menu("Edit");
		others=new Menu("Others");
		option=new Menu("Option");
		view=new Menu("View");
		//font=new Menu("Font");
		
		bold=new CheckboxMenuItem("Bold");
		italic=new CheckboxMenuItem("Italic");
		statusbar=new CheckboxMenuItem("Status Bar");
		bold.addItemListener(this);
		italic.addItemListener(this);
		statusbar.addItemListener(this);
		
		mb.add(file);
		mb.add(edit);
		mb.add(option);
		mb.add(view);
		
		file.add(neu);
		file.add(open);
		file.add(save);
		file.add(saveas);
		file.add(exit);
		
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.addSeparator();
		edit.add(find);
		edit.add(replace);
		
		option.add(bold);
		option.add(italic);
		option.add(others);
		option.add(setbackground);
		
		others.add(selectAll);
		others.add(two);
		
		view.add(statusbar);
		
		f.setMenuBar(mb);
		f.add(ta);
		f.addWindowListener(this);
		f.setTitle("UnTitled.txt");
		temp=new String();
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object o=e.getSource();
		try
		{
		
		
		if(o==neu)
		{
			if(change==true)
			{
				reminderBox();
			}
			
			newrequested=true;
		}
		
		else if(o==open)
		{
			FileDialog fd=new FileDialog(f,"Open a File",FileDialog.LOAD);
			fd.setVisible(true);
			
			String a,b;
			
			a=fd.getDirectory();
			b=fd.getFile();
			
			if(a==null || b==null)
			{
				return;
			}
			
			ta.selectAll();
			int k,j;
			k=ta.getSelectionStart();
			j=ta.getSelectionEnd();
			
			ta.replaceRange("",k,j);
			
			FileInputStream fis=new FileInputStream(a+"/"+b);
			
			int i;
			while((i=fis.read())!=-1)
			{
				ta.append(""+(char)i);
			}
			
			f.setTitle(a+b);
		}
		
		else if(o==save)
		{
			
			if(f.getTitle().compareTo("UnTitled.txt")!=0)
			{
				String a=f.getTitle();
				
				
				FileWriter fos=new FileWriter(a);
			
				String c=ta.getText();
				
				for(int i=0;i<c.length();i++)
				{
					fos.write(c.charAt(i));
				}
			}
			
			else
			{
			
					saveDialogBox();
			}
				
		}
		
		else if(o==saveas)
		{
			saveDialogBox();			
		}
		
		else if(o==exit)
		{
			if(f.getTitle().compareTo("UnTitled.txt")==0 || change==true)
			{
				reminderBox();
				windowclose=true;
			}
			else
			{
			
				System.exit(1);
			}	
		}
		
		else if(o==cut)
		{
			int i,j,enters=0;
			
			i=ta.getSelectionStart();
			j=ta.getSelectionEnd();
			
			//System.out.println(i+"\t"+j);
			
			temp=ta.getSelectedText();
			enters=num_of_enters(i);
			i-=enters;
			j-=enters;
			
			ta.replaceRange("",i,j);
			incut=true;
			
			paste.setEnabled(true);
		}
		
		else if(o==copy)
		{
			temp=ta.getSelectedText();
			paste.setEnabled(true);
		}
		
		else if(o==paste)
		{
			int i,enters;
			
			i=ta.getSelectionStart();
			enters=num_of_enters(i);
			i-=enters;
			ta.replaceRange(temp,i,i);
			
			if(incut==true)
			{
				paste.setEnabled(false);
			}
			
		}
		
		else if(o==setbackground)
		{
		
			ModAdjustBackground ab=new ModAdjustBackground(f);
		}
		
		
		
		
		else if(o==yes)
		{
			dg.setVisible(false);
			dg.dispose();
		
			saveDialogBox();
			
			if(windowclose==true)
			{
				System.exit(1);
			}
			
			if(newrequested=true)
			{
				ta.selectAll();
				int i,j;
				i=ta.getSelectionStart();
				j=ta.getSelectionEnd();
			
				ta.replaceRange("",i,j);
			
				//System.out.println(f.getTitle());
			
				change=false;
			}
			
		}
		
		else if(o==no)
		{
			dg.setVisible(false);
			dg.dispose();
			
			if(windowclose==true)
			{
				System.exit(1);
			}
			
			if(newrequested=true)
			{
				ta.selectAll();
				int i,j;
				i=ta.getSelectionStart();
				j=ta.getSelectionEnd();
			
				ta.replaceRange("",i,j);
			
				//System.out.println(f.getTitle());
			
				change=false;
			}
			
		}
		
		else if(o==cancel)
		{
			dg.setVisible(false);
			dg.dispose();
			
		}
		
		else if(o==find)
		{
			findDialogBox(false);
		}
		
		else if(o==findnext)
		{
			findnextDialogbox(false);
				
		}
		
		else if(o==replace)
		{
			findDialogBox(true);
		}
		
		else if(o==selectAll)
		{
			ta.selectAll();
		}
		
		else if(o==replace1)
		{
			findnextDialogbox(true);
			
		}
		
		else if(o==replaceall)
		{
			dg.dispose();
			String tacontent=new String();
			ta.selectAll();
			tacontent=ta.getSelectedText();
			String st=new String();
			st=tf.getText();
			
			prev=0;
			
			while(prev!=-1)
			{
				prev=tacontent.indexOf(st,(prev+1));
			
				int enters=0,c;
				
				//System.out.println(st.length());
				//System.out.println(prev);
				
				enters=num_of_enters(prev);
			
				c=prev-enters;
				
				String st1=new String();
				st1=tf1.getText();
				ta.replaceRange(st1,c,(c+st.length()));			
			}
		}
		
		/*else if(o==ok)
		{
			dg_child.dispose();
		}*/
		
		}	
		
		
		catch(Exception ex)
		{
			System.out.println("!!YOU CANNOT SLOVE THIS MATTER CONTACT THE CREATER!!");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	
	}
	
	public static void setColor(Color c)
	{
		ta.setBackground(c);
	}
	
	public void itemStateChanged(ItemEvent ex)
	{
		Object o=ex.getSource();
		
		if(o==bold)
		{
			if(bold.getState()==false)
			{
				if(italic.getState()==true)
				{
					ta.setFont(new Font("Arial",Font.ITALIC,14));
				}
				
				else
				{
					ta.setFont(new Font("Arial",Font.PLAIN,14));
				}
			}
			
			else
			{
				if(italic.getState()==true)
				{
					ta.setFont(new Font("Arial",Font.BOLD | Font.ITALIC ,14));
				}
				
				else
				{
					ta.setFont(new Font("Arial",Font.BOLD,14));
				}
			}
		}
		
		else if(o==italic)
		{
			if(bold.getState()==false)
			{
				if(italic.getState()==true)
				{
					ta.setFont(new Font("Arial",Font.ITALIC,14));
				}
				
				else
				{
					ta.setFont(new Font("Arial",Font.PLAIN,14));
				}
			}
			
			else
			{
				if(italic.getState()==true)
				{
					ta.setFont(new Font("Arial",Font.BOLD | Font.ITALIC ,14));
				}
				
				else
				{
					ta.setFont(new Font("Arial",Font.BOLD,14));
				}
			}
		}	
		
		else
		{
			if(statusbar.getState()==true)
			{
				
				//System.out.println("status bar is true");
			
			p=new Panel();
			p.setLayout(new GridLayout(2,2));
			p.setBackground(Color.yellow);
			//f.add(p,"South");
			
			l1=new Label();
			l2=new Label();
			l3=new Label();
			l4=new Label();
			
			
			l1.setText("Words:");
			l2.setText("Characters:");
			l3.setText("Vowels:");
			l4.setText("Alphabhets:");
			
			
			p.add(l1);
			p.add(l2);
			p.add(l3);
			p.add(l4);
			
			p.setVisible(true);
			f.add(p,"South");
			
			counter();
			
			
			}
			
			else
			{
				f.remove(p);
			}	
		}
		
	} 
	
	public void windowClosing(WindowEvent e)
	{
		
		try
		{
			if(f.getTitle().compareTo("UnTitled.txt")==0 || change==true)
			{
				reminderBox();
				windowclose=true;
			}
			else
			{
			
				System.exit(1);
			}	
		
		}
		
		catch(Exception ex)
		{
			System.out.println("!!YOU CANNOT SLOVE THIS MATTER CONTACT THE CREATER!!");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	void counter()
	{
			String st;
		
			st=ta.getText();
		
			char ch[]=st.toCharArray();
			int countw,countc,countv,counta;
			countw=countc=countv=counta=0;
		
			for(int i=0;i<ch.length;i++)
			{
				if(ch[i]==' ')
				{
					countw++;
				}
			
				if((ch[i]>65 && ch[i]<90) || (ch[i]>97 && ch[i]<122))
				{
					counta++;
				}
			
				switch(ch[i])
				{
					case 'a':
					case 'A':
					case 'e':
					case 'E':
					case 'i':
					case 'I':
					case 'o':
					case 'O':
					case 'u':
					case 'U':
				
								countv++;	
				}
			
				countc++;
			}
		
			l1.setText("Words:"+countw);
			l2.setText("Characters:"+countc);
			l3.setText("Vowels:"+countv);
			l4.setText("Alphabhets:"+counta);
	}
	
	public void textValueChanged(TextEvent e)
	{
		change=true;
		
		if(statusbar.getState()==true)
		{
			counter();
		}
		
	}
	
	int num_of_enters(int range)
	{
		ta.selectAll();
		String st1=ta.getSelectedText();
		char ch[]=st1.toCharArray();
		int enters=0;
		
		for(int i=0;i<range;i++)
		{
			if(ch[i]=='\n')
			{
				enters+=1;
			}	
		}
		
		return enters;
	}
	
	void reminderBox()
	{
		dg=new Dialog(f);
		dg.setSize(30,100);
		dg.setLocation(50,50);
		
		Panel p1,p2;
		p1=new Panel();
		p2=new Panel();
		
		yes=new Button("Ok");
		no=new Button("No");
		cancel=new Button("Cancel");
		
		Label l=new Label("Do You Want To Save");
		p1.setLayout(new GridLayout(1,1));
		p1.add(l);
		p2.setLayout(new GridLayout(1,3));
		p2.add(yes);
		p2.add(no);
		p2.add(cancel);
		
		
		yes.addActionListener(this);
		no.addActionListener(this);
		cancel.addActionListener(this);
		
		dg.setLayout(new BorderLayout());
		dg.add(p2,"South");
		dg.add(p1);
		
		dg.setVisible(true);
		 
	}
	
	void findnextDialogbox(boolean b)
	{
		
			String tacontent=new String();
			ta.selectAll();
			tacontent=ta.getSelectedText();
			String st=new String();
			st=tf.getText();
			
			prev=tacontent.indexOf(st,(prev+1));
			//System.out.println(st.length());
			//System.out.println(prev);
			
			if(prev==-1)
			{
				/*Dialog dg1=new Dialog(dg);
				dg1.setSize(50,20);
				dg.setLocation(50,50);
				Label lb1=new Label();
				lb1.setText("STring not Found");
				
				dg1.add(l1);
				dg1.addWindowListener();*/
				
				System.out.println("String not found"); 
			}
			else
			{
				int enters=0,c;
				enters=num_of_enters(prev);
			
				c=prev-enters;
				if(b)
				{	    
					String st1=new String();
					st1=tf1.getText();
					ta.replaceRange(st1,c,(c+st.length()));	
				}
				else
				{
					dg.dispose();
					ta.select(c,(c+st.length()));
				}			
			}
	}
	
	/*void child(Dialog dg)
	{
		dg_child=new Dialog(dg);
		dg_child(50,50);
		dg_child(100,100);
		Label dgl=new Label("CAN'T FIND YOUR STRING");
		ok=new Button("OK");
		
		dg_child.setLayout(new BorderLayout());
		dg_child.add(ok,"South");
		dg_child.add(dgl);
		ok.addActionListener();
		
		dg_child.setVisible(true);
	}*/
	
	void findDialogBox(boolean b)
	{
			//prev=0;
			dg=new Dialog(f);
			dg.setLayout(new BorderLayout());
			dg.setSize(300,100);
			dg.setLocation(150,100);
			Panel p1,p2,p3,p4;
			p1=new Panel();
			p2=new Panel();
			
			if(b)
			{
				prev=-1;
				p3=new Panel();
				p4=new Panel();
				p3.setLayout(new BorderLayout());
				p4.setLayout(new BorderLayout());	
				findnext=new Button("Find Next");
				cancel=new Button("Cancel");
				replace1=new Button("Replace");
				replaceall=new Button("replace all");
				tf=new TextField(10);
				tf1=new TextField(10);
				Label f1=new Label("Find What");
				Label f2=new Label("Replace With");
				p3.add(f1,"West");
				p3.add(tf);
				p4.add(f2,"West");
				p4.add(tf1);
				p1.setLayout(new GridLayout(2,1));
				p1.add(p3);
				p1.add(p4);
				p2.setLayout(new GridLayout(1,4));
				p2.add(findnext);
				p2.add(replace1);
				p2.add(replaceall);
				p2.add(cancel);
				
				findnext.addActionListener(this);
				cancel.addActionListener(this);
				replace1.addActionListener(this);
				replaceall.addActionListener(this);	
			}
			
			else
			{
				prev=-1;
			
				p1.setLayout(new BorderLayout());
				p2.setLayout(new GridLayout(1,2));
			
				Label fn=new Label("Find What:");
				tf=new TextField(10);
			
				p1.add(fn,"West");
				p1.add(tf);
			
				findnext=new Button("Find Next");
				cancel=new Button("Cancel");
				findnext.addActionListener(this);
				cancel.addActionListener(this);
			
				p2.add(findnext);
				p2.add(cancel);
			
			}
			dg.add(p2,"South");
			dg.add(p1);
			dg.setVisible(true);
			//dg.addWindowListener();
			//ta.selectAll();
	}
	
	void saveDialogBox()
	{
		
		try
		{
		
				FileDialog fd=new FileDialog(f,"Save a File",FileDialog.SAVE);
				fd.setVisible(true);
			
				String a,b;
				FileOutputStream fos;
				
				/*if(f.getTitle().compareTo("UnTitled.txt")!=0)
				{
					a=f.getTitle();
					b=new String("");
				
				
					fos=new FileOutputStream(a);
				}*/
				//else
				//{
					
			
					a=fd.getDirectory();
					b=fd.getFile();
				
					if(a==null || b==null)
					{
						return;
					}
			
					fos=new FileOutputStream(a+"/"+b);
			    //}		
			
				String c=ta.getText();
				
				for(int i=0;i<c.length();i++)
				{
					fos.write(c.charAt(i));
				}
				
				f.setTitle(a+b);
				
		}
		
		catch(Exception ex)
		{
			System.out.println("!!YOU CANNOT SLOVE THIS MATTER CONTACT THE CREATER!!");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public static void main(String arg[])
	{
		ModTextEditor d=new ModTextEditor();
	}
}