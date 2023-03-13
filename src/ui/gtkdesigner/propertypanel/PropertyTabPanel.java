package ui.gtkdesigner.propertypanel;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import ui.widgets.IGtkWidget;
import utils.Nullable;

@SuppressWarnings("serial")
public abstract class PropertyTabPanel extends JPanel
{
	public static final String NO_PROP_DESC = "No property description available.";
	
	protected PropertyPanel propPanel;
	
	public PropertyTabPanel(PropertyPanel propPanel)
	{
		this.propPanel = propPanel;
		
		setLayout(null);
		setBackground(Color.black);
	}
	
	/**
	 * Adds a checkbox to this panel.
	 * @param bounds The bounds of the checkbox. This parameter may equal {@code null}.
	 * @param text The text displayed next to the check box
	 * @param icon The icon displayed. This parameter may equal {@code null}.
	 * @param hoverHint The text that is shown when the users hovers over the checkbox
	 * @param selected The initial selected state
	 * @param onStateChanged The state change callback for the new checkbox. This parameter may equal {@code null}.
	 * @return The newly created checkbox
	 */
	protected JCheckBox addCheckBox(@Nullable Rectangle bounds, String text, @Nullable Icon icon, String hoverHint,
			boolean selected, @Nullable Consumer<Boolean> onStateChanged)
	{
		JCheckBox box = new JCheckBox(text, icon, selected);
		
		if(bounds != null)
			box.setBounds(bounds);
		
		box.setToolTipText(hoverHint);
		box.setForeground(Color.white);
		box.setBackground(Color.black);
		
		if(onStateChanged != null)
			box.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e)
				{
					onStateChanged.accept(box.isSelected());
				}
				
			});
		this.add(box);
		return box;
	}
	
	public abstract void refresh(IGtkWidget data);
	
	protected Consumer<Boolean> createCheckBoxCallback(String property)
	{
		return (checked)-> {
			IGtkWidget gtkWidget = propPanel.getWidget();
			
			assert gtkWidget != null; /* This can not equal null becuase no GtkWidgetTab should exist if no GtkWidget is selected in the PropertyPanel. */
			
			System.out.println("Updated property " + property + " to " + checked);
			
			gtkWidget.setProperty(property, checked);
		};
	}
	
	public abstract void initComponents();
}
