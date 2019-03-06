package nlp2code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * class InputHandler
 *  Implements the required functionality to search for a query via the search button in the eclipse toolbar.
 */
public class InputHandler extends AbstractHandler {
	// Holds history of previous code snippets (from previous queries) to enable undo functionality.
	static Vector<String> previous_search = new Vector<String>();
	// Holds the previous query (equivilent to previous_search[last]).
	static String previous_query = "";
	// Offset of the previous query (to re-insert when using undo).
	static int previous_offset = 0;
	// Length of the previous query (to re-insert when using undo).
	static int previous_length = 0;
	// Holds previous queries.
	static Vector<String> previous_queries = new Vector<String>();
	// Listens for when cycling finishes and prompts for feedback afterwards.
	static CycleDocListener doclistener = new CycleDocListener();
	// Create a listener to handle searches via the editor in ?{query}? format.
	static QueryDocListener qdl = new QueryDocListener();
	// A vector containing all documents that have an active query document listener.
	static Vector<IDocument> documents = new Vector<IDocument>();
	ArrayList<String> results = new ArrayList<String>();
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
				        int offset = doc.getLineOffset(doc.getNumberOfLines()-1);
				        
				        if ( sel instanceof TextSelection ) {
				            final TextSelection textSel = (TextSelection)sel;
				            String txt=textSel.getText();
				            MessageDialog.openInformation(
									window.getShell(),
									"Selected text",
									"Your selected text is\n\n" + txt);
				            
				            scrap(txt);
				            System.out.println("hi");
				        }
				        
				        for(String x: results)
				        {
				        	MessageDialog dialog = new MessageDialog(window.getShell(),"Codes",null,x,MessageDialog.INFORMATION, new String[] {"Next Code","Close","Copy"},0);
				        	
				        	int a =dialog.open();
				        	//System.out.println(a);
				        	
				        	if(a==0)
				        	{
				        		continue;
				        	}
				        	else if(a==2)
				        	{
				        		doc.replace(offset, 0, x+"\n");
				        		break;
				        	}
				        	else
				        	{
				        		break;
				        	}
				        }
				        
				    }
				} 
				
				catch( Exception ex ){
				    ex.printStackTrace();
				}
				return null;
			}
			private void scrap(String txt) {
				Document doc = null;
				try {
					 doc = Jsoup.connect("http://code-search.uni.lu/facoy").data("q",txt).timeout(10 * 1000).get();
					 for (org.jsoup.nodes.Element item : doc.select("div.snippet_item table.highlighttable tr")) {
						String data_item=item.select(".code").text();
						results.add(data_item);
						//System.out.println(data_item);
						//System.out.println("1111");
					}
					 
				} catch (IOException e) {
					System.out.println("Submission failed");
					e.printStackTrace();
				}
				System.out.println("hi2");
			//}
			
}
}