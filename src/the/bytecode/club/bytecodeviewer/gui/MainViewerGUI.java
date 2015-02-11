package the.bytecode.club.bytecodeviewer.gui;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.filechooser.FileFilter;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JCheckBoxMenuItem;

import org.objectweb.asm.tree.ClassNode;

import the.bytecode.club.bytecodeviewer.BytecodeViewer;
import the.bytecode.club.bytecodeviewer.Dex2Jar;
import the.bytecode.club.bytecodeviewer.FileChangeNotifier;
import the.bytecode.club.bytecodeviewer.JarUtils;
import the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler;
import the.bytecode.club.bytecodeviewer.decompilers.FernFlowerDecompiler;
import the.bytecode.club.bytecodeviewer.decompilers.JavaDecompiler;
import the.bytecode.club.bytecodeviewer.decompilers.KrakatauDecompiler;
import the.bytecode.club.bytecodeviewer.decompilers.ProcyonDecompiler;
import the.bytecode.club.bytecodeviewer.obfuscators.RenameClasses;
import the.bytecode.club.bytecodeviewer.obfuscators.RenameFields;
import the.bytecode.club.bytecodeviewer.obfuscators.RenameMethods;
import the.bytecode.club.bytecodeviewer.plugins.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JRadioButtonMenuItem;

public class MainViewerGUI extends JFrame implements FileChangeNotifier {
	
	public void pythonC() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new PythonCFileFilter());
		fc.setFileHidingEnabled(false);
		fc.setAcceptAllFileFilterUsed(false);
		int returnVal = fc.showOpenDialog(BytecodeViewer.viewer);

		if (returnVal == JFileChooser.APPROVE_OPTION)
			try {
				BytecodeViewer.python = fc.getSelectedFile().getAbsolutePath();
			} catch (Exception e1) {
				new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e1);
			}
	}
	
	public void rtC() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new RTCFileFilter());
		fc.setFileHidingEnabled(false);
		fc.setAcceptAllFileFilterUsed(false);
		int returnVal = fc.showOpenDialog(BytecodeViewer.viewer);

		if (returnVal == JFileChooser.APPROVE_OPTION)
			try {
				BytecodeViewer.rt = fc.getSelectedFile().getAbsolutePath();
			} catch (Exception e1) {
				new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e1);
			}
	}

	final JavaDecompiler ff_dc = new FernFlowerDecompiler();
	final JavaDecompiler proc_dc = new ProcyonDecompiler();
	final JavaDecompiler cfr_dc = new CFRDecompiler();
	final JavaDecompiler krak_dc = new KrakatauDecompiler();

	private static final long serialVersionUID = 1851409230530948543L;
	public JCheckBoxMenuItem debugHelpers = new JCheckBoxMenuItem(
			"Debug Helpers");
	private JSplitPane sp1;
	private JSplitPane sp2;
	static ArrayList<VisibleComponent> rfComps = new ArrayList<VisibleComponent>();
	public JCheckBoxMenuItem rbr = new JCheckBoxMenuItem("Hide bridge methods");
	public JCheckBoxMenuItem rsy = new JCheckBoxMenuItem(
			"Hide synthetic class members");
	public JCheckBoxMenuItem din = new JCheckBoxMenuItem(
			"Decompile inner classes");
	public JCheckBoxMenuItem dc4 = new JCheckBoxMenuItem(
			"Collapse 1.4 class references");
	public JCheckBoxMenuItem das = new JCheckBoxMenuItem("Decompile assertions");
	public JCheckBoxMenuItem hes = new JCheckBoxMenuItem(
			"Hide empty super invocation");
	public JCheckBoxMenuItem hdc = new JCheckBoxMenuItem(
			"Hide empty default constructor");
	public JCheckBoxMenuItem dgs = new JCheckBoxMenuItem(
			"Decompile generic signatures");
	public JCheckBoxMenuItem ner = new JCheckBoxMenuItem(
			"Assume return not throwing exceptions");
	public JCheckBoxMenuItem den = new JCheckBoxMenuItem(
			"Decompile enumerations");
	public JCheckBoxMenuItem rgn = new JCheckBoxMenuItem(
			"Remove getClass() invocation");
	public JCheckBoxMenuItem bto = new JCheckBoxMenuItem(
			"Interpret int 1 as boolean true");
	public JCheckBoxMenuItem nns = new JCheckBoxMenuItem(
			"Allow for not set synthetic attribute");
	public JCheckBoxMenuItem uto = new JCheckBoxMenuItem(
			"Consider nameless types as java.lang.Object");
	public JCheckBoxMenuItem udv = new JCheckBoxMenuItem(
			"Reconstruct variable names from debug info");
	public JCheckBoxMenuItem rer = new JCheckBoxMenuItem(
			"Remove empty exception ranges");
	public JCheckBoxMenuItem fdi = new JCheckBoxMenuItem(
			"Deinline finally structures");
	public JCheckBoxMenuItem asc = new JCheckBoxMenuItem(
			"Allow only ASCII characters in strings");
	private final JMenuItem mntmNewWorkspace = new JMenuItem("New Workspace");
	public JMenu mnRecentFiles = new JMenu("Recent Files");
	private final JMenuItem mntmNewMenuItem = new JMenuItem(
			"Save Java Files As..");
	private final JMenuItem mntmAbout = new JMenuItem("About");
	private AboutWindow aboutWindow = new AboutWindow();
	private final JSeparator separator_3 = new JSeparator();
	private final JMenu mnNewMenu_1 = new JMenu("Plugins");
	private final JMenuItem mntmStartExternalPlugin = new JMenuItem(
			"Open Plugin..");
	private final JSeparator separator_4 = new JSeparator();
	public JMenu mnRecentPlugins = new JMenu("Recent Plugins");
	private final JSeparator separator_5 = new JSeparator();
	private final JMenuItem mntmStartZkmString = new JMenuItem(
			"ZKM String Decrypter");
	private final JMenuItem mntmNewMenuItem_1 = new JMenuItem(
			"Malicious Code Scanner");
	private final JMenuItem mntmNewMenuItem_2 = new JMenuItem(
			"Allatori String Decrypter");
	private final JMenuItem mntmShowAllStrings = new JMenuItem(
			"Show All Strings");
	private final JMenuItem mntmShowMainMethods = new JMenuItem(
			"Show Main Methods");
	private final JMenuItem mntmNewMenuItem_3 = new JMenuItem("Save As Jar..");
	private JMenuBar menuBar = new JMenuBar();
	private final JMenuItem mntmReplaceStrings = new JMenuItem(
			"Replace Strings");
	private final JMenuItem mntmNewMenuItem_4 = new JMenuItem("");
	private final JMenu mnNewMenu_3 = new JMenu("CFR");
	private final JMenu mnNewMenu_4 = new JMenu("Procyon");
	public final JCheckBoxMenuItem decodeenumswitch = new JCheckBoxMenuItem(
			"Decode Enum Switch");
	public final JCheckBoxMenuItem sugarenums = new JCheckBoxMenuItem(
			"SugarEnums");
	public final JCheckBoxMenuItem decodestringswitch = new JCheckBoxMenuItem(
			"Decode String Switch");
	public final JCheckBoxMenuItem arrayiter = new JCheckBoxMenuItem(
			"Arrayiter");
	public final JCheckBoxMenuItem collectioniter = new JCheckBoxMenuItem(
			"Collectioniter");
	public final JCheckBoxMenuItem innerclasses = new JCheckBoxMenuItem(
			"Inner Classes");
	public final JCheckBoxMenuItem removeboilerplate = new JCheckBoxMenuItem(
			"Remove Boiler Plate");
	public final JCheckBoxMenuItem removeinnerclasssynthetics = new JCheckBoxMenuItem(
			"Remove Inner Class Synthetics");
	public final JCheckBoxMenuItem decodelambdas = new JCheckBoxMenuItem(
			"Decode Lambdas");
	public final JCheckBoxMenuItem hidebridgemethods = new JCheckBoxMenuItem(
			"Hide Bridge Methods");
	public final JCheckBoxMenuItem liftconstructorinit = new JCheckBoxMenuItem(
			"Lift  Constructor Init");
	public final JCheckBoxMenuItem removedeadmethods = new JCheckBoxMenuItem(
			"Remove Dead Methods");
	public final JCheckBoxMenuItem removebadgenerics = new JCheckBoxMenuItem(
			"Remove Bad Generics");
	public final JCheckBoxMenuItem sugarasserts = new JCheckBoxMenuItem(
			"Sugar Asserts");
	public final JCheckBoxMenuItem sugarboxing = new JCheckBoxMenuItem(
			"Sugar Boxing");
	public final JCheckBoxMenuItem showversion = new JCheckBoxMenuItem(
			"Show Version");
	public final JCheckBoxMenuItem decodefinally = new JCheckBoxMenuItem(
			"Decode Finally");
	public final JCheckBoxMenuItem tidymonitors = new JCheckBoxMenuItem(
			"Tidy Monitors");
	public final JCheckBoxMenuItem lenient = new JCheckBoxMenuItem("Lenient");
	public final JCheckBoxMenuItem dumpclasspath = new JCheckBoxMenuItem(
			"Dump Classpath");
	public final JCheckBoxMenuItem comments = new JCheckBoxMenuItem("Comments");
	public final JCheckBoxMenuItem forcetopsort = new JCheckBoxMenuItem(
			"Force Top Sort");
	public final JCheckBoxMenuItem forcetopsortaggress = new JCheckBoxMenuItem(
			"Force Top Sort Aggress");
	public final JCheckBoxMenuItem stringbuffer = new JCheckBoxMenuItem(
			"String Buffer");
	public final JCheckBoxMenuItem stringbuilder = new JCheckBoxMenuItem(
			"String Builder");
	public final JCheckBoxMenuItem silent = new JCheckBoxMenuItem("Silent");
	public final JCheckBoxMenuItem recover = new JCheckBoxMenuItem("Recover");
	public final JCheckBoxMenuItem eclipse = new JCheckBoxMenuItem("Eclipse");
	public final JCheckBoxMenuItem override = new JCheckBoxMenuItem("Override");
	public final JCheckBoxMenuItem showinferrable = new JCheckBoxMenuItem(
			"Show Inferrable");
	public final JCheckBoxMenuItem aexagg = new JCheckBoxMenuItem("Aexagg");
	public final JCheckBoxMenuItem forcecondpropagate = new JCheckBoxMenuItem(
			"Force Cond Propagate");
	public final JCheckBoxMenuItem hideutf = new JCheckBoxMenuItem("Hide UTF");
	public final JCheckBoxMenuItem hidelongstrings = new JCheckBoxMenuItem(
			"Hide Long Strings");
	public final JCheckBoxMenuItem commentmonitor = new JCheckBoxMenuItem(
			"Comment Monitors");
	public final JCheckBoxMenuItem allowcorrecting = new JCheckBoxMenuItem(
			"Allow Correcting");
	public final JCheckBoxMenuItem labelledblocks = new JCheckBoxMenuItem(
			"Labelled Blocks");
	public final JCheckBoxMenuItem j14classobj = new JCheckBoxMenuItem(
			"J14ClassOBJ");
	public final JCheckBoxMenuItem hidelangimports = new JCheckBoxMenuItem(
			"Hide Lang Imports");
	public final JCheckBoxMenuItem recoverytypeclash = new JCheckBoxMenuItem(
			"Recover Type Clash");
	public final JCheckBoxMenuItem recoverytypehints = new JCheckBoxMenuItem(
			"Recover Type  Hints");
	public final JCheckBoxMenuItem forceturningifs = new JCheckBoxMenuItem(
			"Force Returning IFs");
	public final JCheckBoxMenuItem forloopaggcapture = new JCheckBoxMenuItem(
			"For Loop AGG Capture");
	public final JCheckBoxMenuItem forceexceptionprune = new JCheckBoxMenuItem(
			"Force Exception Prune");
	public final JCheckBoxMenuItem chckbxmntmShowDebugLine = new JCheckBoxMenuItem(
			"Show Debug Line Numbers");
	public final JCheckBoxMenuItem chckbxmntmSimplifyMemberReferences = new JCheckBoxMenuItem(
			"Simplify Member References");
	public final JCheckBoxMenuItem mnMergeVariables = new JCheckBoxMenuItem(
			"Merge Variables");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_1 = new JCheckBoxMenuItem(
			"Unicode Output Enabled");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_2 = new JCheckBoxMenuItem(
			"Retain Pointless Switches");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_3 = new JCheckBoxMenuItem(
			"Include Line Numbers In Bytecode");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_4 = new JCheckBoxMenuItem(
			"Include Error Diagnostics");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_5 = new JCheckBoxMenuItem(
			"Retain Redundant Casts");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_6 = new JCheckBoxMenuItem(
			"Always Generate Exception Variable For Catch Blocks");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_7 = new JCheckBoxMenuItem(
			"Show Synthetic Members");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_8 = new JCheckBoxMenuItem(
			"Force Explicit Type Arguments");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_9 = new JCheckBoxMenuItem(
			"Force Explicit Imports");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_10 = new JCheckBoxMenuItem(
			"Flatten Switch Blocks");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_11 = new JCheckBoxMenuItem(
			"Exclude Nested Types");
	public final JCheckBoxMenuItem chckbxmntmAppendBrackets = new JCheckBoxMenuItem(
			"Append Brackets To Labels");
	public final JCheckBoxMenuItem chckbxmntmNewCheckItem_12 = new JCheckBoxMenuItem(
			"Update Check");
	private final JMenuItem mntmNewMenuItem_5 = new JMenuItem("EZ Inject");
	private final JMenu mnNewMenu_5 = new JMenu("Obfuscate");
	private final JMenuItem mntmNewMenuItem_6 = new JMenuItem("Rename Fields");
	private final JMenuItem mntmNewMenuItem_7 = new JMenuItem("Rename Methods");
	private final JMenuItem mntmNewMenuItem_8 = new JMenuItem(
			"Move All Classes Into Root Package");
	private final JMenuItem mntmNewMenuItem_9 = new JMenuItem("Control Flow");
	private final JMenuItem mntmNewMenuItem_10 = new JMenuItem("Junk Code");
	public final ButtonGroup obfuscatorGroup = new ButtonGroup();
	public final JRadioButtonMenuItem strongObf = new JRadioButtonMenuItem(
			"Strong Obfuscation");
	public final JRadioButtonMenuItem lightObf = new JRadioButtonMenuItem(
			"Light Obfuscation");
	private final JMenuItem mntmNewMenuItem_11 = new JMenuItem("Rename Classes");
	private final JSeparator separator_2 = new JSeparator();
	public final ButtonGroup panelGroup1 = new ButtonGroup();
	public final ButtonGroup panelGroup2 = new ButtonGroup();
	public final ButtonGroup panelGroup3 = new ButtonGroup();
	private final JMenu mnNewMenu_6 = new JMenu("View Panes");
	private final JMenu mnNewMenu_7 = new JMenu("Pane 1");
	private final JMenu mnNewMenu_8 = new JMenu("Pane 2");
	private final JMenu mnNewMenu_9 = new JMenu("Pane 3");
	public final JRadioButtonMenuItem panel1None = new JRadioButtonMenuItem(
			"None");
	public final JRadioButtonMenuItem panel1Hexcode = new JRadioButtonMenuItem(
			"Hexcode");
	public final JRadioButtonMenuItem panel1Bytecode = new JRadioButtonMenuItem(
			"Bytecode");
	public final JRadioButtonMenuItem panel1Fern = new JRadioButtonMenuItem(
			"FernFlower");
	public final JRadioButtonMenuItem panel1CFR = new JRadioButtonMenuItem(
			"CFR");
	public final JRadioButtonMenuItem panel1Proc = new JRadioButtonMenuItem(
			"Procyon");
	public final JRadioButtonMenuItem panel2None = new JRadioButtonMenuItem(
			"None");
	public final JRadioButtonMenuItem panel2Proc = new JRadioButtonMenuItem(
			"Procyon");
	public final JRadioButtonMenuItem panel2CFR = new JRadioButtonMenuItem(
			"CFR");
	public final JRadioButtonMenuItem panel2Bytecode = new JRadioButtonMenuItem(
			"Bytecode");
	public final JRadioButtonMenuItem panel2Fern = new JRadioButtonMenuItem(
			"FernFlower");
	public final JRadioButtonMenuItem panel2Hexcode = new JRadioButtonMenuItem(
			"Hexcode");
	public final JRadioButtonMenuItem panel3None = new JRadioButtonMenuItem(
			"None");
	public final JRadioButtonMenuItem panel3Proc = new JRadioButtonMenuItem(
			"Procyon");
	public final JRadioButtonMenuItem panel3CFR = new JRadioButtonMenuItem(
			"CFR");
	public final JRadioButtonMenuItem panel3Fern = new JRadioButtonMenuItem(
			"FernFlower");
	public final JRadioButtonMenuItem panel3Bytecode = new JRadioButtonMenuItem(
			"Bytecode");
	public final JRadioButtonMenuItem panel3Hexcode = new JRadioButtonMenuItem(
			"Hexcode");
	private final JMenuItem mntmNewMenuItem_12 = new JMenuItem("Save Java File..");
	public WorkPane workPane = new WorkPane(this);
	private final JMenu mnSettings = new JMenu("Settings");
	private final JSeparator separator_6 = new JSeparator();
	public final JCheckBoxMenuItem refreshOnChange = new JCheckBoxMenuItem("Refresh On View Change");

	public boolean isMaximized = false;
	
	public void removed(boolean busy) {
		if (busy) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			for (Component c : this.getComponents())
				c.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			sp1.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			sp2.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			for (VisibleComponent c : rfComps) {
				c.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				if (c instanceof WorkPane) {
					WorkPane w = (WorkPane) c;
					for (Component c2 : w.tabs.getComponents())
						c2.setCursor(Cursor
								.getPredefinedCursor(Cursor.WAIT_CURSOR));
				}
			}
		} else {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			for (Component c : this.getComponents())
				c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			sp1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			sp2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			for (VisibleComponent c : rfComps) {
				c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if (c instanceof WorkPane) {
					WorkPane w = (WorkPane) c;
					for (Component c2 : w.tabs.getComponents())
						c2.setCursor(Cursor
								.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		}
	}
	
    private class Test implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
        	checkKey(e);
            return false;
        }
    }

	long last = System.currentTimeMillis();
    public void checkKey(KeyEvent e) {
    	if(System.currentTimeMillis() - last <= (1000 * 4))
    		return;
    	
        if ((e.getKeyCode() == KeyEvent.VK_O) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
        	last = System.currentTimeMillis();
        	JFileChooser fc = new JFileChooser();
        	try {
        		fc.setSelectedFile(new File(BytecodeViewer.lastDirectory));
        	} catch(Exception e2) {
        		
        	}
			fc.setFileFilter(new APKDEXJarZipClassFileFilter());
			fc.setFileHidingEnabled(false);
			fc.setAcceptAllFileFilterUsed(false);
			int returnVal = fc.showOpenDialog(BytecodeViewer.viewer);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				BytecodeViewer.lastDirectory = fc.getSelectedFile().getAbsolutePath();
				try {
					BytecodeViewer.viewer.setIcon(true);
					BytecodeViewer.openFiles(new File[] { fc.getSelectedFile() }, true);
					BytecodeViewer.viewer.setIcon(false);
				} catch (Exception e1) {
					new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e1);
				}
			}
        } else if ((e.getKeyCode() == KeyEvent.VK_N) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
        	last = System.currentTimeMillis();
        	BytecodeViewer.resetWorkSpace(true);
        }
    }

	ImageIcon busy = new ImageIcon(getClass().getResource("/resources/1.gif"));
	ImageIcon busyB64 = new ImageIcon(BytecodeViewer.b642IMG("R0lGODlhEAALAPQAAP///wAAANra2tDQ0Orq6gcHBwAAAC8vL4KCgmFhYbq6uiMjI0tLS4qKimVlZb6+vicnJwUFBU9PT+bm5tjY2PT09Dk5Odzc3PLy8ra2tqCgoMrKyu7u7gAAAAAAAAAAACH5BAkLAAAAIf4aQ3JlYXRlZCB3aXRoIGFqYXhsb2FkLmluZm8AIf8LTkVUU0NBUEUyLjADAQAAACwAAAAAEAALAAAFLSAgjmRpnqSgCuLKAq5AEIM4zDVw03ve27ifDgfkEYe04kDIDC5zrtYKRa2WQgAh+QQJCwAAACwAAAAAEAALAAAFJGBhGAVgnqhpHIeRvsDawqns0qeN5+y967tYLyicBYE7EYkYAgAh+QQJCwAAACwAAAAAEAALAAAFNiAgjothLOOIJAkiGgxjpGKiKMkbz7SN6zIawJcDwIK9W/HISxGBzdHTuBNOmcJVCyoUlk7CEAAh+QQJCwAAACwAAAAAEAALAAAFNSAgjqQIRRFUAo3jNGIkSdHqPI8Tz3V55zuaDacDyIQ+YrBH+hWPzJFzOQQaeavWi7oqnVIhACH5BAkLAAAALAAAAAAQAAsAAAUyICCOZGme1rJY5kRRk7hI0mJSVUXJtF3iOl7tltsBZsNfUegjAY3I5sgFY55KqdX1GgIAIfkECQsAAAAsAAAAABAACwAABTcgII5kaZ4kcV2EqLJipmnZhWGXaOOitm2aXQ4g7P2Ct2ER4AMul00kj5g0Al8tADY2y6C+4FIIACH5BAkLAAAALAAAAAAQAAsAAAUvICCOZGme5ERRk6iy7qpyHCVStA3gNa/7txxwlwv2isSacYUc+l4tADQGQ1mvpBAAIfkECQsAAAAsAAAAABAACwAABS8gII5kaZ7kRFGTqLLuqnIcJVK0DeA1r/u3HHCXC/aKxJpxhRz6Xi0ANAZDWa+kEAA7"));
	private final JMenuItem mntmSaveAsApk = new JMenuItem("Save As DEX..");
	private final JMenuItem mntmCodeSequenceDiagram = new JMenuItem("Code Sequence Diagram");
	private final JSeparator separator_7 = new JSeparator();
	private final JSeparator separator_8 = new JSeparator();
	private final JSeparator separator_9 = new JSeparator();
	private final JSeparator separator_10 = new JSeparator();
	private final JSeparator separator_11 = new JSeparator();
	private final JSeparator separator_12 = new JSeparator();
	public final JRadioButtonMenuItem panel1Smali = new JRadioButtonMenuItem("Smali Editable");
	public final JRadioButtonMenuItem panel2Smali = new JRadioButtonMenuItem("Smali Editable");
	public final JRadioButtonMenuItem panel3Smali = new JRadioButtonMenuItem("Smali Editable");
	public final JCheckBoxMenuItem autoCompileSmali = new JCheckBoxMenuItem("Compile On Save");
	private final JMenuItem mntmNewMenuItem_13 = new JMenuItem("Compile");
	public final JCheckBoxMenuItem autoCompileOnRefresh = new JCheckBoxMenuItem("Compile On Refresh");
	private final JMenuItem mntmSetPythonDirectory = new JMenuItem("Set Python 2.7 Executable");
	private final JSeparator separator_13 = new JSeparator();
	public final JRadioButtonMenuItem panel1Krakatau = new JRadioButtonMenuItem("Krakatau");
	public final JRadioButtonMenuItem panel1KrakatauEditable = new JRadioButtonMenuItem("Krakatau Editable");
	public final JRadioButtonMenuItem panel2Krakatau = new JRadioButtonMenuItem("Krakatau");
	public final JRadioButtonMenuItem panel2KrakatauEditable = new JRadioButtonMenuItem("Krakatau Editable");
	public final JRadioButtonMenuItem panel3Krakatau = new JRadioButtonMenuItem("Krakatau");
	public final JRadioButtonMenuItem panel3KrakatauEditable = new JRadioButtonMenuItem("Krakatau Editable");
	private final JMenuItem mntmSetJreRt = new JMenuItem("Set JRE RT Library");
	private final JSeparator separator_14 = new JSeparator();
	private final JCheckBoxMenuItem chckbxmntmPaneEditable = new JCheckBoxMenuItem("Pane 1 Editable");
	private final JCheckBoxMenuItem chckbxmntmPaneEditable_1 = new JCheckBoxMenuItem("Pane 2 Editable");
	private final JCheckBoxMenuItem chckbxmntmPaneEditable_2 = new JCheckBoxMenuItem("Pane 3 Editable");
	public void setIcon(final boolean busy) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (busy) {
					try {
						mntmNewMenuItem_4.setIcon(BytecodeViewer.viewer.busy);
					} catch (NullPointerException e) {
						mntmNewMenuItem_4.setIcon(busyB64);
					}
				} else
					mntmNewMenuItem_4.setIcon(null);
				mntmNewMenuItem_4.updateUI();
			}
		});
	}

	public MainViewerGUI() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new Test());
		mnNewMenu_5.setVisible(false);
		this.addWindowStateListener(new WindowAdapter() {
		      public void windowStateChanged(WindowEvent evt) {
		          int oldState = evt.getOldState();
		          int newState = evt.getNewState();

		          if ((oldState & Frame.ICONIFIED) == 0 && (newState & Frame.ICONIFIED) != 0) {
		            //System.out.println("Frame was iconized");
		          } else if ((oldState & Frame.ICONIFIED) != 0 && (newState & Frame.ICONIFIED) == 0) {
		            //System.out.println("Frame was deiconized");
		          }

		          if ((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0) {
		        	  isMaximized = true;
		          } else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0) {
		        	  isMaximized = false;
		          }
		        }
		      });
		this.setIconImages(BytecodeViewer.iconList);
		panelGroup1.add(panel1None);
		panelGroup1.add(panel1Fern);
		panelGroup1.add(panel1Proc);
		panelGroup1.add(panel1CFR);
		panelGroup1.add(panel1Bytecode);
		panelGroup1.add(panel1Smali);
		panelGroup1.add(panel1Hexcode);
		panelGroup1.add(panel1Krakatau);
		panelGroup1.add(panel1KrakatauEditable);
		panelGroup1.setSelected(panel1Proc.getModel(), true);//my one true love
		panelGroup2.add(panel2None);
		panelGroup2.add(panel2Fern);
		panelGroup2.add(panel2Proc);
		panelGroup2.add(panel2CFR);
		panelGroup2.add(panel2Bytecode);
		panelGroup2.add(panel2Smali);
		panelGroup2.add(panel2Hexcode);
		panelGroup2.add(panel2Krakatau);
		panelGroup2.add(panel2KrakatauEditable);
		panelGroup2.setSelected(panel2Bytecode.getModel(), true);
		panelGroup3.add(panel3None);
		panelGroup3.add(panel3Fern);
		panelGroup3.add(panel3Proc);
		panelGroup3.add(panel3CFR);
		panelGroup3.add(panel3Bytecode);
		panelGroup3.add(panel3Smali);
		panelGroup3.add(panel3Hexcode);
		panelGroup3.add(panel3Krakatau);
		panelGroup3.add(panel3KrakatauEditable);
		panelGroup3.setSelected(panel3None.getModel(), true);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(refreshOnChange.isSelected()) {
					if(workPane.getCurrentClass() == null)
						return;

					workPane.refreshClass.doClick();
				}
			}
			
		};

		panel1None.addActionListener(listener);
		panel1Fern.addActionListener(listener);
		panel1Proc.addActionListener(listener);
		panel1CFR.addActionListener(listener);
		panel1Bytecode.addActionListener(listener);
		panel1Smali.addActionListener(listener);
		panel1Hexcode.addActionListener(listener);
		panel1Krakatau.addActionListener(listener);
		panel1KrakatauEditable.addActionListener(listener);
		panel2None.addActionListener(listener);
		panel2Fern.addActionListener(listener);
		panel2Proc.addActionListener(listener);
		panel2CFR.addActionListener(listener);
		panel2Bytecode.addActionListener(listener);
		panel2Smali.addActionListener(listener);
		panel2Hexcode.addActionListener(listener);
		panel2Krakatau.addActionListener(listener);
		panel2KrakatauEditable.addActionListener(listener);
		panel3None.addActionListener(listener);
		panel3Fern.addActionListener(listener);
		panel3Proc.addActionListener(listener);
		panel3CFR.addActionListener(listener);
		panel3Bytecode.addActionListener(listener);
		panel3Smali.addActionListener(listener);
		panel3Hexcode.addActionListener(listener);
		panel3Krakatau.addActionListener(listener);
		panel3KrakatauEditable.addActionListener(listener);
		obfuscatorGroup.add(strongObf);
		obfuscatorGroup.add(lightObf);
		obfuscatorGroup.setSelected(strongObf.getModel(), true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// procyon
		/* none */

		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		mntmNewWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BytecodeViewer.resetWorkSpace(true);
			}
		});

		JMenuItem mntmLoadJar = new JMenuItem("Add..");
		mntmLoadJar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
            	try {
            		File f = new File(BytecodeViewer.lastDirectory);
            		if(f.exists())
            			fc.setSelectedFile(f);
            	} catch(Exception e2) {
            		
            	}
				fc.setFileFilter(new APKDEXJarZipClassFileFilter());
				fc.setFileHidingEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(BytecodeViewer.viewer);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					BytecodeViewer.lastDirectory = fc.getSelectedFile().getAbsolutePath();
					try {
						BytecodeViewer.viewer.setIcon(true);
						BytecodeViewer.openFiles(new File[] { fc
								.getSelectedFile() }, true);
						BytecodeViewer.viewer.setIcon(false);
					} catch (Exception e1) {
						new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e1);
					}
				}
			}
		});
		mnNewMenu.add(mntmLoadJar);

		mnNewMenu.add(mntmNewWorkspace);

		JMenuItem mntmSave = new JMenuItem("Save Files As..");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(BytecodeViewer.getLoadedClasses().isEmpty()) {
					BytecodeViewer.showMessage("First open a class, jar, zip, apk or dex file.");
					return;
				}
				if(autoCompileSmali.isSelected() && !BytecodeViewer.compile(false))
					return;
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new ZipFileFilter());
				fc.setFileHidingEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showSaveDialog(MainViewerGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if(!file.getAbsolutePath().endsWith(".zip"))
						file = new File(file.getAbsolutePath()+".zip");
					
					if(file.exists()) {
						JOptionPane pane = new JOptionPane(
								"Are you sure you wish to overwrite this existing file?");
						Object[] options = new String[] { "Yes", "No" };
						pane.setOptions(options);
						JDialog dialog = pane.createDialog(BytecodeViewer.viewer,
								"Bytecode Viewer - Overwrite File");
						dialog.setVisible(true);
						Object obj = pane.getValue();
						int result = -1;
						for (int k = 0; k < options.length; k++)
							if (options[k].equals(obj))
								result = k;

						if (result == 0) {
							file.delete();
						} else {
							return;
						}
					}
					
					final File file2 = file;
					
					BytecodeViewer.viewer.setIcon(true);
					Thread t = new Thread() {
						@Override
						public void run() {
							JarUtils.saveAsJar(BytecodeViewer.getLoadedClasses(),
									file2.getAbsolutePath());
							BytecodeViewer.viewer.setIcon(false);
						}
					};
					t.start();
				}
			}
		});

		mnNewMenu.add(separator_3);
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(BytecodeViewer.getLoadedClasses().isEmpty()) {
					BytecodeViewer.showMessage("First open a class, jar, zip, apk or dex file.");
					return;
				}
				if(autoCompileSmali.isSelected() && !BytecodeViewer.compile(false))
					return;
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new JarFileFilter());
				fc.setFileHidingEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showSaveDialog(MainViewerGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					String path = file.getAbsolutePath();
					if (!path.endsWith(".jar"))
						path = path + ".jar";
					
					if(new File(path).exists()) {
						JOptionPane pane = new JOptionPane(
								"Are you sure you wish to overwrite this existing file?");
						Object[] options = new String[] { "Yes", "No" };
						pane.setOptions(options);
						JDialog dialog = pane.createDialog(BytecodeViewer.viewer,
								"Bytecode Viewer - Overwrite File");
						dialog.setVisible(true);
						Object obj = pane.getValue();
						int result = -1;
						for (int k = 0; k < options.length; k++)
							if (options[k].equals(obj))
								result = k;

						if (result == 0) {
							file.delete();
						} else {
							return;
						}
					}
					
					new ExportJar(path).setVisible(true);
				}
			}
		});
		mntmNewMenuItem_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BytecodeViewer.compile(true);
			}
		});
		
		mnNewMenu.add(mntmNewMenuItem_13);

		mnNewMenu.add(mntmNewMenuItem_3);
		mntmSaveAsApk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(BytecodeViewer.getLoadedClasses().isEmpty()) {
					BytecodeViewer.showMessage("First open a class, jar, zip, apk or dex file.");
					return;
				}
				if(autoCompileSmali.isSelected() && !BytecodeViewer.compile(false))
					return;
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new DexFileFilter());
				fc.setFileHidingEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showSaveDialog(MainViewerGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					String output = file.getAbsolutePath();
					if (!output.endsWith(".dex"))
						output = output + ".dex";
					
					final File file2 = new File(output);
					
					if(file2.exists()) {
						JOptionPane pane = new JOptionPane(
								"Are you sure you wish to overwrite this existing file?");
						Object[] options = new String[] { "Yes", "No" };
						pane.setOptions(options);
						JDialog dialog = pane.createDialog(BytecodeViewer.viewer,
								"Bytecode Viewer - Overwrite File");
						dialog.setVisible(true);
						Object obj = pane.getValue();
						int result = -1;
						for (int k = 0; k < options.length; k++)
							if (options[k].equals(obj))
								result = k;

						if (result == 0) {
							file.delete();
						} else {
							return;
						}
					}
						
					Thread t = new Thread() {
						@Override
						public void run() {
							BytecodeViewer.viewer.setIcon(true);
							final String input = BytecodeViewer.tempDirectory+BytecodeViewer.fs+BytecodeViewer.getRandomizedName()+".jar";
							JarUtils.saveAsJar(BytecodeViewer.getLoadedClasses(), input);
							
							Thread t = new Thread() {
								@Override
								public void run() {
									Dex2Jar.saveAsDex(new File(input), file2);
									BytecodeViewer.viewer.setIcon(false);
								}
							};
							t.start();
						}
					};
					t.start();
				}
			}
		});
		
		mnNewMenu.add(mntmSaveAsApk);
		mnNewMenu.add(mntmSave);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(BytecodeViewer.getLoadedClasses().isEmpty()) {
					BytecodeViewer.showMessage("First open a class, jar, zip, apk or dex file.");
					return;
				}
				if(autoCompileSmali.isSelected() && !BytecodeViewer.compile(false))
					return;
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new ZipFileFilter());
				fc.setFileHidingEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showSaveDialog(MainViewerGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if(!file.getAbsolutePath().endsWith(".zip"))
						file = new File(file.getAbsolutePath()+".zip");
					
					if(file.exists()) {
						JOptionPane pane = new JOptionPane(
								"Are you sure you wish to overwrite this existing file?");
						Object[] options = new String[] { "Yes", "No" };
						pane.setOptions(options);
						JDialog dialog = pane.createDialog(BytecodeViewer.viewer,
								"Bytecode Viewer - Overwrite File");
						dialog.setVisible(true);
						Object obj = pane.getValue();
						int result = -1;
						for (int k = 0; k < options.length; k++)
							if (options[k].equals(obj))
								result = k;

						if (result == 0) {
							file.delete();
						} else {
							return;
						}
					}
					
					BytecodeViewer.viewer.setIcon(true);
					final String path = appendZip(file);// cheap hax cause
														// string is final

					JOptionPane pane = new JOptionPane(
							"What decompiler will you use?");
					Object[] options = new String[] { "Procyon", "CFR",
							"Fernflower", "Krakatau", "Cancel" };
					pane.setOptions(options);
					JDialog dialog = pane.createDialog(BytecodeViewer.viewer,
							"Bytecode Viewer - Select Decompiler");
					dialog.setVisible(true);
					Object obj = pane.getValue();
					int result = -1;
					for (int k = 0; k < options.length; k++)
						if (options[k].equals(obj))
							result = k;

					if (result == 0) {
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									proc_dc.decompileToZip(path);
									BytecodeViewer.viewer.setIcon(false);
								} catch (Exception e) {
									new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
								}
							}
						};
						t.start();
					}
					if (result == 1) {
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									cfr_dc.decompileToZip(path);
									BytecodeViewer.viewer.setIcon(false);
								} catch (Exception e) {
									new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
								}
							}
						};
						t.start();
					}
					if (result == 2) {
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									ff_dc.decompileToZip(path);
									BytecodeViewer.viewer.setIcon(false);
								} catch (Exception e) {
									new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
								}
							}
						};
						t.start();
					}
					
					if (result == 3) {
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									krak_dc.decompileToZip(path);
									BytecodeViewer.viewer.setIcon(false);
								} catch (Exception e) {
									new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
								}
							}
						};
						t.start();
					}
					
					if(result == 4) {
						BytecodeViewer.viewer.setIcon(false);
					}
				}
			}
		});
		mntmNewMenuItem_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(workPane.getCurrentClass() == null) {
					BytecodeViewer.showMessage("First open a class, jar, zip, apk or dex file.");
					return;
				}
				if(autoCompileSmali.isSelected() && !BytecodeViewer.compile(false))
					return;
				final String s = workPane.getCurrentClass().name;
				
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new JavaFileFilter());
				fc.setFileHidingEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showSaveDialog(MainViewerGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					BytecodeViewer.viewer.setIcon(true);
					final String path = appendJava(file);// cheap hax cause
														// string is final
					
					if(new File(path).exists()) {
						JOptionPane pane = new JOptionPane(
								"Are you sure you wish to overwrite this existing file?");
						Object[] options = new String[] { "Yes", "No" };
						pane.setOptions(options);
						JDialog dialog = pane.createDialog(BytecodeViewer.viewer,
								"Bytecode Viewer - Overwrite File");
						dialog.setVisible(true);
						Object obj = pane.getValue();
						int result = -1;
						for (int k = 0; k < options.length; k++)
							if (options[k].equals(obj))
								result = k;

						if (result == 0) {
							file.delete();
						} else {
							return;
						}
					}
					
					JOptionPane pane = new JOptionPane(
							"What decompiler will you use?");
					Object[] options = new String[] { "Procyon", "CFR",
							"Fernflower", "Krakatau", "Cancel" };
					pane.setOptions(options);
					JDialog dialog = pane.createDialog(BytecodeViewer.viewer,
							"Bytecode Viewer - Select Decompiler");
					dialog.setVisible(true);
					Object obj = pane.getValue();
					int result = -1;
					for (int k = 0; k < options.length; k++)
						if (options[k].equals(obj))
							result = k;
					
					if (result == 0) {
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									proc_dc.decompileToClass(s,path);
									BytecodeViewer.viewer.setIcon(false);
								} catch (Exception e) {
									new the.bytecode.club.bytecodeviewer.api.ExceptionUI(
											e);
								}
							}
						};
						t.start();
					}
					if (result == 1) {
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									cfr_dc.decompileToClass(s,path);
									BytecodeViewer.viewer.setIcon(false);
								} catch (Exception e) {
									new the.bytecode.club.bytecodeviewer.api.ExceptionUI(
											e);
								}
							}
						};
						t.start();
					}
					if (result == 2) {
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									ff_dc.decompileToClass(s,path);
									BytecodeViewer.viewer.setIcon(false);
								} catch (Exception e) {
									new the.bytecode.club.bytecodeviewer.api.ExceptionUI(
											e);
								}
							}
						};
						t.start();
					}
					if (result == 3) {
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									krak_dc.decompileToClass(s,path);
									BytecodeViewer.viewer.setIcon(false);
								} catch (Exception e) {
									new the.bytecode.club.bytecodeviewer.api.ExceptionUI(
											e);
								}
							}
						};
						t.start();
					}
					
					if(result == 4) {
						BytecodeViewer.viewer.setIcon(false);
					}
				}
			}
		});
		
		mnNewMenu.add(mntmNewMenuItem_12);

		mnNewMenu.add(mntmNewMenuItem);

		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);

		mnNewMenu.add(mnRecentFiles);

		JSeparator separator_1 = new JSeparator();
		mnNewMenu.add(separator_1);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aboutWindow.setVisible(true);
			}
		});

		mnNewMenu.add(mntmAbout);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane pane = new JOptionPane(
						"Are you sure you want to exit?");
				Object[] options = new String[] { "Yes", "No" };
				pane.setOptions(options);
				JDialog dialog = pane.createDialog(BytecodeViewer.viewer,
						"Bytecode Viewer - Exit");
				dialog.setVisible(true);
				Object obj = pane.getValue();
				int result = -1;
				for (int k = 0; k < options.length; k++)
					if (options[k].equals(obj))
						result = k;

				if (result == 0) {
					System.exit(0);
				}
			}
		});
		mnNewMenu.add(mntmExit);

		menuBar.add(mnNewMenu_6);
		
		mnNewMenu_6.add(chckbxmntmPaneEditable);
		
		mnNewMenu_6.add(chckbxmntmPaneEditable_1);
		
		mnNewMenu_6.add(chckbxmntmPaneEditable_2);
		
		mnNewMenu_6.add(separator_14);

		mnNewMenu_6.add(mnNewMenu_7);

		mnNewMenu_7.add(panel1None);
		
		mnNewMenu_7.add(separator_7);

		mnNewMenu_7.add(panel1Proc);

		mnNewMenu_7.add(panel1CFR);

		mnNewMenu_7.add(panel1Fern);
		
		mnNewMenu_7.add(panel1Krakatau);
		
		mnNewMenu_7.add(separator_8);

		mnNewMenu_7.add(panel1Bytecode);
		
		mnNewMenu_7.add(panel1KrakatauEditable);
		
		mnNewMenu_7.add(panel1Smali);

		mnNewMenu_7.add(panel1Hexcode);

		mnNewMenu_6.add(mnNewMenu_8);

		mnNewMenu_8.add(panel2None);
		
		mnNewMenu_8.add(separator_9);

		mnNewMenu_8.add(panel2Proc);

		mnNewMenu_8.add(panel2CFR);

		mnNewMenu_8.add(panel2Fern);
		
		mnNewMenu_8.add(panel2Krakatau);
		
		mnNewMenu_8.add(separator_10);

		mnNewMenu_8.add(panel2Bytecode);
		
		mnNewMenu_8.add(panel2KrakatauEditable);
		
		mnNewMenu_8.add(panel2Smali);

		mnNewMenu_8.add(panel2Hexcode);

		mnNewMenu_6.add(mnNewMenu_9);

		mnNewMenu_9.add(panel3None);
		
		mnNewMenu_9.add(separator_11);

		mnNewMenu_9.add(panel3Proc);

		mnNewMenu_9.add(panel3CFR);

		mnNewMenu_9.add(panel3Fern);
		
		mnNewMenu_9.add(panel3Krakatau);
		
		mnNewMenu_9.add(separator_12);
		
		mnNewMenu_9.add(panel3KrakatauEditable);

		mnNewMenu_9.add(panel3Bytecode);
		
		mnNewMenu_9.add(panel3Smali);

		mnNewMenu_9.add(panel3Hexcode);
		
		menuBar.add(mnSettings);
				
				mnSettings.add(autoCompileSmali);
				
				mnSettings.add(autoCompileOnRefresh);
				mnSettings.add(chckbxmntmNewCheckItem_12);
				chckbxmntmNewCheckItem_12.setSelected(true);
				
				mnSettings.add(refreshOnChange);
				
				mnSettings.add(separator_13);
				mntmSetPythonDirectory.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						pythonC();
					}
				});
				
				mnSettings.add(mntmSetPythonDirectory);
				mntmSetJreRt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						rtC();
					}
				});
				
				mnSettings.add(mntmSetJreRt);
				
				mnSettings.add(separator_6);
				mnSettings.add(mnNewMenu_4);
		
				mnNewMenu_4.add(chckbxmntmNewCheckItem_6);
				
						mnNewMenu_4.add(chckbxmntmNewCheckItem_11);
						
								mnNewMenu_4.add(chckbxmntmShowDebugLine);
								
										mnNewMenu_4.add(chckbxmntmNewCheckItem_3);
										
												mnNewMenu_4.add(chckbxmntmNewCheckItem_4);
												
														mnNewMenu_4.add(chckbxmntmNewCheckItem_7);
														
																mnNewMenu_4.add(chckbxmntmSimplifyMemberReferences);
																
																		mnNewMenu_4.add(mnMergeVariables);
																		
																				mnNewMenu_4.add(chckbxmntmNewCheckItem_8);
																				
																						mnNewMenu_4.add(chckbxmntmNewCheckItem_9);
																						
																								mnNewMenu_4.add(chckbxmntmNewCheckItem_10);
																								
																										mnNewMenu_4.add(chckbxmntmNewCheckItem_2);
																										
																												mnNewMenu_4.add(chckbxmntmNewCheckItem_5);
																												
																														mnNewMenu_4.add(chckbxmntmNewCheckItem_1);
		// cfr
		decodeenumswitch.setSelected(true);
		sugarenums.setSelected(true);
		decodestringswitch.setSelected(true);
		arrayiter.setSelected(true);
		collectioniter.setSelected(true);
		innerclasses.setSelected(true);
		removeboilerplate.setSelected(true);
		removeinnerclasssynthetics.setSelected(true);
		decodelambdas.setSelected(true);
		hidebridgemethods.setSelected(true);
		liftconstructorinit.setSelected(true);
		removedeadmethods.setSelected(true);
		removebadgenerics.setSelected(true);
		sugarasserts.setSelected(true);
		sugarboxing.setSelected(true);
		showversion.setSelected(true);
		decodefinally.setSelected(true);
		tidymonitors.setSelected(true);
		lenient.setSelected(false);
		dumpclasspath.setSelected(false);
		comments.setSelected(true);
		forcetopsort.setSelected(true);
		forcetopsortaggress.setSelected(true);
		forceexceptionprune.setSelected(true);
		stringbuffer.setSelected(false);
		stringbuilder.setSelected(true);
		silent.setSelected(true);
		recover.setSelected(true);
		eclipse.setSelected(true);
		override.setSelected(true);
		showinferrable.setSelected(true);
		aexagg.setSelected(true);
		forcecondpropagate.setSelected(true);
		hideutf.setSelected(true);
		hidelongstrings.setSelected(false);
		commentmonitor.setSelected(false);
		allowcorrecting.setSelected(true);
		labelledblocks.setSelected(true);
		j14classobj.setSelected(false);
		hidelangimports.setSelected(true);
		recoverytypeclash.setSelected(true);
		recoverytypehints.setSelected(true);
		forceturningifs.setSelected(true);
		forloopaggcapture.setSelected(true);
		mnSettings.add(mnNewMenu_3);

		mnNewMenu_3.add(decodeenumswitch);

		mnNewMenu_3.add(sugarenums);

		mnNewMenu_3.add(decodestringswitch);

		mnNewMenu_3.add(arrayiter);

		mnNewMenu_3.add(collectioniter);

		mnNewMenu_3.add(innerclasses);

		mnNewMenu_3.add(removeboilerplate);

		mnNewMenu_3.add(removeinnerclasssynthetics);

		mnNewMenu_3.add(decodelambdas);

		mnNewMenu_3.add(hidebridgemethods);

		mnNewMenu_3.add(liftconstructorinit);

		mnNewMenu_3.add(removedeadmethods);

		mnNewMenu_3.add(removebadgenerics);

		mnNewMenu_3.add(sugarasserts);

		mnNewMenu_3.add(sugarboxing);

		mnNewMenu_3.add(showversion);

		mnNewMenu_3.add(decodefinally);

		mnNewMenu_3.add(tidymonitors);

		mnNewMenu_3.add(lenient);

		mnNewMenu_3.add(dumpclasspath);

		mnNewMenu_3.add(comments);

		mnNewMenu_3.add(forcetopsort);

		mnNewMenu_3.add(forcetopsortaggress);

		mnNewMenu_3.add(forceexceptionprune);

		mnNewMenu_3.add(stringbuffer);

		mnNewMenu_3.add(stringbuilder);

		mnNewMenu_3.add(silent);

		mnNewMenu_3.add(recover);

		mnNewMenu_3.add(eclipse);

		mnNewMenu_3.add(override);

		mnNewMenu_3.add(showinferrable);

		mnNewMenu_3.add(aexagg);

		mnNewMenu_3.add(forcecondpropagate);

		mnNewMenu_3.add(hideutf);

		mnNewMenu_3.add(hidelongstrings);

		mnNewMenu_3.add(commentmonitor);

		mnNewMenu_3.add(allowcorrecting);

		mnNewMenu_3.add(labelledblocks);

		mnNewMenu_3.add(j14classobj);

		mnNewMenu_3.add(hidelangimports);

		mnNewMenu_3.add(recoverytypeclash);

		mnNewMenu_3.add(recoverytypehints);

		mnNewMenu_3.add(forceturningifs);

		mnNewMenu_3.add(forloopaggcapture);
		// fernflower
		rbr.setSelected(true);
		rsy.setSelected(false);
		din.setSelected(true);
		das.setSelected(true);
		dgs.setSelected(false);
		den.setSelected(true);
		uto.setSelected(true);
		udv.setSelected(true);
		fdi.setSelected(true);
		asc.setSelected(false);

		JMenu mnDecompilerSettings = new JMenu("FernFlower");
		mnSettings.add(mnDecompilerSettings);
		dc4.setSelected(true);
		mnDecompilerSettings.add(dc4);
		nns.setSelected(true);
		mnDecompilerSettings.add(nns);
		ner.setSelected(true);
		mnDecompilerSettings.add(ner);
		bto.setSelected(true);
		mnDecompilerSettings.add(bto);
		rgn.setSelected(true);
		mnDecompilerSettings.add(rgn);
		rer.setSelected(true);
		mnDecompilerSettings.add(rer);
		mnDecompilerSettings.add(rbr);
		mnDecompilerSettings.add(rsy);
		hes.setSelected(true);
		mnDecompilerSettings.add(hes);
		hdc.setSelected(true);
		mnDecompilerSettings.add(hdc);
		mnDecompilerSettings.add(din);
		mnDecompilerSettings.add(das);
		mnDecompilerSettings.add(dgs);
		mnDecompilerSettings.add(den);
		mnDecompilerSettings.add(uto);
		mnDecompilerSettings.add(udv);
		mnDecompilerSettings.add(fdi);
		mnDecompilerSettings.add(asc);
		debugHelpers.setSelected(true);
		// other
		chckbxmntmAppendBrackets.setSelected(true);

		JMenu mnBytecodeDecompilerSettings = new JMenu("Bytecode Decompiler");
		mnSettings.add(mnBytecodeDecompilerSettings);

		mnBytecodeDecompilerSettings.add(debugHelpers);

		mnBytecodeDecompilerSettings.add(chckbxmntmAppendBrackets);
		
		menuBar.add(mnNewMenu_5);
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (BytecodeViewer.runningObfuscation) {
					BytecodeViewer.showMessage("You're currently running an obfuscation task, wait for this to finish.");
					return;
				}
				new RenameFields().start();
			}
		});

		mnNewMenu_5.add(strongObf);

		mnNewMenu_5.add(lightObf);

		mnNewMenu_5.add(separator_2);
		mntmNewMenuItem_8.setEnabled(false);

		mnNewMenu_5.add(mntmNewMenuItem_8);

		mnNewMenu_5.add(mntmNewMenuItem_6);
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (BytecodeViewer.runningObfuscation) {
					BytecodeViewer.showMessage("You're currently running an obfuscation task, wait for this to finish.");
					return;
				}
				new RenameMethods().start();
			}
		});

		mnNewMenu_5.add(mntmNewMenuItem_7);
		mntmNewMenuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (BytecodeViewer.runningObfuscation) {
					BytecodeViewer.showMessage("You're currently running an obfuscation task, wait for this to finish.");
					return;
				}
				new RenameClasses().start();
			}
		});

		mnNewMenu_5.add(mntmNewMenuItem_11);
		mntmNewMenuItem_9.setEnabled(false);

		mnNewMenu_5.add(mntmNewMenuItem_9);
		mntmNewMenuItem_10.setEnabled(false);

		mnNewMenu_5.add(mntmNewMenuItem_10);

		menuBar.add(mnNewMenu_1);
		mnNewMenu_1.add(mntmStartExternalPlugin);
		mnNewMenu_1.add(separator_4);
		mnNewMenu_1.add(mnRecentPlugins);
		mnNewMenu_1.add(separator_5);
		mntmCodeSequenceDiagram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!BytecodeViewer.loadedClasses.isEmpty())
					PluginManager.runPlugin(new CodeSequenceDiagram());
				else {
					System.out.println("Plugin not ran, put some classes in first.");
					BytecodeViewer.showMessage("Plugin not ran, put some classes in first.");
				}
			}
		});
		
		mnNewMenu_1.add(mntmCodeSequenceDiagram);
		mnNewMenu_1.add(mntmNewMenuItem_1);
		mnNewMenu_1.add(mntmShowMainMethods);
		mnNewMenu_1.add(mntmShowAllStrings);
		mntmReplaceStrings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!BytecodeViewer.loadedClasses.isEmpty())
					new ReplaceStringsOptions().setVisible(true);
				else {
					System.out.println("Plugin not ran, put some classes in first.");
					BytecodeViewer.showMessage("Plugin not ran, put some classes in first.");
				}
			}
		});

		mnNewMenu_1.add(mntmReplaceStrings);
		mnNewMenu_1.add(mntmNewMenuItem_2);
		mnNewMenu_1.add(mntmStartZkmString);
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!BytecodeViewer.loadedClasses.isEmpty())
					new EZInjectionOptions().setVisible(true);
				else {
					System.out.println("Plugin not ran, put some classes in first.");
					BytecodeViewer.showMessage("Plugin not ran, put some classes in first.");
				}
			}
		});

		mnNewMenu_1.add(mntmNewMenuItem_5);

		menuBar.add(mntmNewMenuItem_4);

		mntmStartExternalPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new GroovyFileFilter());
				fc.setFileHidingEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(BytecodeViewer.viewer);

				if (returnVal == JFileChooser.APPROVE_OPTION)
					try {
						BytecodeViewer.viewer.setIcon(true);
						BytecodeViewer.startPlugin(fc.getSelectedFile());
						BytecodeViewer.viewer.setIcon(false);
					} catch (Exception e1) {
						new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e1);
					}
			}
		});
		mntmStartZkmString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PluginManager.runPlugin(new ZKMStringDecrypter());
			}
		});
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PluginManager.runPlugin(new AllatoriStringDecrypter());
			}
		});
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!BytecodeViewer.loadedClasses.isEmpty())
					new MaliciousCodeScannerOptions().setVisible(true);
				else {
					System.out.println("Plugin not ran, put some classes in first.");
					BytecodeViewer.showMessage("Plugin not ran, put some classes in first.");
				}
			}
		});
		mntmShowAllStrings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PluginManager.runPlugin(new ShowAllStrings());
			}
		});

		mntmShowMainMethods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PluginManager.runPlugin(new ShowMainMethods());
			}
		});

		setSize(new Dimension(800, 400));
		setTitle("Bytecode Viewer "+BytecodeViewer.version+" - https://bytecodeviewer.com | https://the.bytecode.club - @Konloch");
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		// scrollPane.setViewportView(tree);
		FileNavigationPane cn = new FileNavigationPane(this);
		cn.setMinimumSize(new Dimension(200, 50));
		// panel.add(cn);
		SearchingPane s = new SearchingPane(this);
		s.setPreferredSize(new Dimension(200, 50));
		s.setMinimumSize(new Dimension(200, 50));
		s.setMaximumSize(new Dimension(200, 2147483647));
		// panel.add(s);
		sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cn, s);
		// panel.add(sp1);
		cn.setPreferredSize(new Dimension(200, 50));
		cn.setMaximumSize(new Dimension(200, 2147483647));
		sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, workPane);
		getContentPane().add(sp2);
		sp2.setResizeWeight(0.05);
		sp1.setResizeWeight(0.5);
		rfComps.add(cn);

		rfComps.add(s);
		rfComps.add(workPane);
		this.setLocationRelativeTo(null);
	}

	public String appendZip(File file) {
		String path = file.getAbsolutePath();
		if (!path.endsWith(".zip"))
			path = path + ".zip";
		return path;
	}
	
	public String appendClass(File file) {
		String path = file.getAbsolutePath();
		if (!path.endsWith(".class"))
			path = path + ".class";
		return path;
	}
	
	public String appendJava(File file) {
		String path = file.getAbsolutePath();
		if (!path.endsWith(".java"))
			path = path + ".java";
		return path;
	}

	@Override
	public void openClassFile(final String name, final ClassNode cn) {
		for (final VisibleComponent vc : rfComps) {
			vc.openClassFile(name, cn);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getComponent(final Class<T> clazz) {
		for (final VisibleComponent vc : rfComps) {
			if (vc.getClass() == clazz)
				return (T) vc;
		}
		return null;
	}
	
	public class GroovyPythonRubyFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;

			String extension = getExtension(f);
			if (extension != null)
				return (extension.equals("gy") || extension.equals("groovy")
						|| extension.equals("py") || extension.equals("python")
						|| extension.equals("rb") || extension.equals("ruby"));

			return false;
		}

		@Override
		public String getDescription() {
			return "Groovy, Python or Ruby plugins.";
		}

		public String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1)
				ext = s.substring(i + 1).toLowerCase();

			return ext;
		}
	}

	
	public class GroovyFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;

			String extension = getExtension(f);
			if (extension != null)
				return (extension.equals("gy") || extension.equals("groovy"));

			return false;
		}

		@Override
		public String getDescription() {
			return "Groovy plugins.";
		}

		public String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1)
				ext = s.substring(i + 1).toLowerCase();

			return ext;
		}
	}
	public class APKDEXJarZipClassFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;

			String extension = getExtension(f);
			if (extension != null)
				return (extension.equals("jar")   || extension.equals("zip")
					 || extension.equals("class") || extension.equals("apk")
					 || extension.equals("dex"));

			return false;
		}

		@Override
		public String getDescription() {
			return "APKs, DEX, Class Files or Zip/Jar Archives";
		}

		public String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1)
				ext = s.substring(i + 1).toLowerCase();

			return ext;
		}
	}

	public class ZipFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;

			String extension = getExtension(f);
			if (extension != null)
				return (extension.equals("zip"));

			return false;
		}

		@Override
		public String getDescription() {
			return "Zip Archives";
		}

		public String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1)
				ext = s.substring(i + 1).toLowerCase();

			return ext;
		}
	}

	public class JarFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;

			String extension = getExtension(f);
			if (extension != null)
				return (extension.equals("jar"));

			return false;
		}

		@Override
		public String getDescription() {
			return "Jar Archives";
		}

		public String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1)
				ext = s.substring(i + 1).toLowerCase();

			return ext;
		}
	}

	public class JavaFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;

			String extension = getExtension(f);
			if (extension != null)
				return (extension.equals("java"));

			return false;
		}

		@Override
		public String getDescription() {
			return "Java Source Files";
		}

		public String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1)
				ext = s.substring(i + 1).toLowerCase();

			return ext;
		}
	}

	public class DexFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;

			String extension = getExtension(f);
			if (extension != null)
				return (extension.equals("dex"));

			return false;
		}

		@Override
		public String getDescription() {
			return "Android DEX Files";
		}

		public String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1)
				ext = s.substring(i + 1).toLowerCase();

			return ext;
		}
	}

	public class PythonCFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			return true;
		}

		@Override
		public String getDescription() {
			return "Python 2.7 Executable";
		}
	}

	public class RTCFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			return true;
		}

		@Override
		public String getDescription() {
			return "JRE RT Library";
		}
	}
}