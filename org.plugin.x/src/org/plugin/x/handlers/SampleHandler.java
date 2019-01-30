package org.plugin.x.handlers;
import java.io.IOException;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.jsoup.*;
import org.jsoup.nodes.Document;

public class SampleHandler extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {               
		    IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    if ( part instanceof ITextEditor ) {
		        final ITextEditor editor = (ITextEditor)part;
		        IDocumentProvider prov = editor.getDocumentProvider();
		        IDocument doc = prov.getDocument( editor.getEditorInput() );
		        ISelection sel = editor.getSelectionProvider().getSelection();
		        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			   
		        if ( sel instanceof TextSelection ) {
		            final TextSelection textSel = (TextSelection)sel;
		            String txt=textSel.getText();
		            MessageDialog.openInformation(
							window.getShell(),
							"Selected text",
							txt);
		            
		            scrap(txt);
		        }
		    }
		} catch( Exception ex ){
		    ex.printStackTrace();
		}
		return null;
	}
	private void scrap(String txt) {
		Document doc = null;
		try {
			 doc = Jsoup.connect("http://code-search.uni.lu/facoy").data("q",txt).get();
			  // System.out.println(doc.outerHtml());
			 for (org.jsoup.nodes.Element item : doc.select("div.snippet_item table.highlighttable tr")) {
				String data_item=item.select(".code").text();
				System.out.println(data_item);
			}
			 
		} catch (IOException e) {
			System.out.println("Submission failed");
			e.printStackTrace();
		}
	}
}
