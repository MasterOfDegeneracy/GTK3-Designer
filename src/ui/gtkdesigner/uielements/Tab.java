package ui.gtkdesigner.uielements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tab extends JPanel
{
	private boolean expanded = false;
	
	public Tab(String name, JPanel content)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Tab tab = this;
		
		JButton btn = new JButton(name);
		btn.setBounds(0, 0, 100, 100);
		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				expanded = !expanded;
				
				if(expanded)
				{
					tab.add(content);
					tab.setPreferredSize(new Dimension(tab.getPreferredSize().width,
							btn.getPreferredSize().height + content.getPreferredSize().height));
					tab.revalidate();
				}
				else
				{
					tab.remove(content);
					tab.setPreferredSize(new Dimension(tab.getPreferredSize().width,
							btn.getPreferredSize().height));
					tab.revalidate();
				}
			}
		});
		add(btn);
	}
}
