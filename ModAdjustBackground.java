package Editor;
import java.awt.*;
import java.awt.event.*;


public class ModAdjustBackground implements AdjustmentListener,ActionListener
{
	private Panel p1,p2,p3,p5,p6,p4;
	Dialog d;
	Label l1,l2,l3;
	Button ok,cancel;
	Scrollbar sc1,sc2,sc3;
	Color c;
	
	ModAdjustBackground(Frame f)
	{
		d=new Dialog(f);
		d.setSize(200,200);
		d.setLocation(200,300);
		d.setLayout(new BorderLayout());
		
		
		p1=new Panel();
		p2=new Panel();
		p3=new Panel();
		//p4=new Panel();
		p5=new Panel();
		p6=new Panel();
		//p7=new Panel();
		
		p1.setLayout(new BorderLayout());
		
		
		l1=new Label();
		l2=new Label();
		l3=new Label();
		
		l1.setText("R");
		l2.setText("G");
		l3.setText("B");
		
		p2.add(l1);
		p2.add(l2);
		p2.add(l3);
		
		p3.setLayout(new GridLayout(1,3));
		
		sc1=new Scrollbar(Scrollbar.VERTICAL,0,10,0,255);
		sc2=new Scrollbar(Scrollbar.VERTICAL,0,10,0,255);
		sc3=new Scrollbar(Scrollbar.VERTICAL,0,10,0,255);
		
		sc1.addAdjustmentListener(this);
		sc2.addAdjustmentListener(this);
		sc3.addAdjustmentListener(this);
		
		p3.add(sc1);
		p3.add(sc2);
		p3.add(sc3);
		
		p1.add(p2,"North");
		p1.add(p3);
		p1.setBackground(Color.GREEN);
		
		
		//p4.setLayout(new BorderLayout());
		//p4.add(p1,"East");
		
		/*cb1=new Checkbox("Red");
		cb2=new Checkbox("Green");
		cb3=new Checkbox("Blue");*/
		
		ok=new Button("OK");
		cancel=new Button("Cancel");
		
		p5.setLayout(new GridLayout(1,2));
		p5.add(ok);
		p5.add(cancel);
		
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		p5.setBackground( Color.PINK);
		
		p4=new Panel();
		
		p6.setLayout(new BorderLayout());
		p6.add(p5,"South");
		p6.add(p1,"East");
		p6.add(p4);
		
		d.add(p6);
		
		d.setVisible(true);
	}
	
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		int i,j,k;
		i=sc1.getValue();
		j=sc2.getValue();
		k=sc3.getValue();

		p4.setBackground(new Color(i,j,k));
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object o=e.getSource();
		
		if(o==ok)
		{
			int i,j,k;
			i=sc1.getValue();
			j=sc2.getValue();
			k=sc3.getValue();
		
			c=new Color(i,j,k);
			
			ModTextEditor.setColor(c);
			
			d.setVisible(false);
			d.dispose();
		}
		
		else
		{
			int i,j,k;
			i=255;
			j=255;
			k=255;
		
			c=new Color(i,j,k);
			ModTextEditor.setColor(c);
			
			d.setVisible(false);
			d.dispose();
			
		}
	}
	
}	