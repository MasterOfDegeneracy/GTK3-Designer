package ui.gtkdesigner;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class WidgetPanelTransferHandler extends TransferHandler
{
	@Override
	public int getSourceActions(JComponent c)
	{
		return TransferHandler.COPY;
	}
	
	@Override
	protected Transferable createTransferable(JComponent source)
	{
		return new StringSelection(source.toString());
	}
	
	@Override
	protected void exportDone(JComponent source, Transferable data, int action)
	{
		
	}
	@Override
	public boolean canImport(TransferHandler.TransferSupport support)
	{
		if (!support.isDrop()) {
			return false;
		}
		// only Strings
		if (!support.isDataFlavorSupported(DataFlavor.imageFlavor)) {
			return false;
		}
		return true;
	}
	@Override
	public boolean importData(TransferHandler.TransferSupport support)
	{
		return true;
	}
}