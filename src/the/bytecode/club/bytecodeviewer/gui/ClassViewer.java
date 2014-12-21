package the.bytecode.club.bytecodeviewer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.IconView;
import javax.swing.text.JTextComponent;
import javax.swing.text.LabelView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.ParagraphView;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import com.jhe.hexed.JHexEditor;

import the.bytecode.club.bytecodeviewer.BytecodeViewer;
import the.bytecode.club.bytecodeviewer.decompilers.bytecode.ClassNodeDecompiler;
import the.bytecode.club.bytecodeviewer.decompilers.java.CFRDecompiler;
import the.bytecode.club.bytecodeviewer.decompilers.java.FernFlowerDecompiler;
import the.bytecode.club.bytecodeviewer.decompilers.java.ProcyonDecompiler;

/**
 * This represents the opened classfile.
 * 
 * @author Konloch
 * @author WaterWolf
 * 
 */

public class ClassViewer extends JPanel {

	/**
	 * Whoever wrote this function, THANK YOU!
	 * 
	 * @param splitter
	 * @param proportion
	 * @return
	 */
	public static JSplitPane setDividerLocation(final JSplitPane splitter,
			final double proportion) {
		if (splitter.isShowing()) {
			if (splitter.getWidth() > 0 && splitter.getHeight() > 0) {
				splitter.setDividerLocation(proportion);
			} else {
				splitter.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent ce) {
						splitter.removeComponentListener(this);
						setDividerLocation(splitter, proportion);
					}
				});
			}
		} else {
			splitter.addHierarchyListener(new HierarchyListener() {
				@Override
				public void hierarchyChanged(HierarchyEvent e) {
					if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0
							&& splitter.isShowing()) {
						splitter.removeHierarchyListener(this);
						setDividerLocation(splitter, proportion);
					}
				}
			});
		}
		return splitter;
	}

	private static final long serialVersionUID = -8650495368920680024L;
	ArrayList<MethodData> lnData = new ArrayList<MethodData>();
	String name;
	ClassNode cn;
	JSplitPane sp;
	JSplitPane sp2;
	public JPanel panel1Search = new JPanel(new BorderLayout());
	public JPanel panel2Search = new JPanel(new BorderLayout());
	public JPanel panel3Search = new JPanel(new BorderLayout());
	public JCheckBox check1 = new JCheckBox("Exact");
	public JCheckBox check2 = new JCheckBox("Exact");
	public JCheckBox check3 = new JCheckBox("Exact");
	public JPanel panel1 = new JPanel(new BorderLayout());
	public JPanel panel2 = new JPanel(new BorderLayout());
	public JPanel panel3 = new JPanel(new BorderLayout());
	int pane1 = -1;
	int pane2 = -1;
	int pane3 = -1;

	/**
	 * This was really interesting to write.
	 * 
	 * @author Konloch
	 * 
	 */
	public void search(int pane, String search, boolean next) {
		try {
			Component[] com = null;
			if (pane == 0) // bytecode
				com = panel1.getComponents();
			else if (pane == 1)
				com = panel2.getComponents();
			else if (pane == 2)
				com = panel3.getComponents();

			if (com == null) // someone fucked up, lets prevent a nullpointer.
				return;

			for (Component c : com) {
				if (c instanceof RTextScrollPane) {
					RSyntaxTextArea area = (RSyntaxTextArea) ((RTextScrollPane) c)
							.getViewport().getComponent(0);

					if (search.isEmpty()) {
						highlight(pane, area, "");
						return;
					}

					int startLine = area.getDocument().getDefaultRootElement()
							.getElementIndex(area.getCaretPosition()) + 1;
					int currentLine = 1;
					boolean canSearch = false;
					String[] test = null;
					if (area.getText().split("\n").length >= 2)
						test = area.getText().split("\n");
					else
						test = area.getText().split("\r");
					int lastGoodLine = -1;
					int firstPos = -1;
					boolean found = false;

					if (next) {
						for (String s : test) {
							if (pane == 0 && !check1.isSelected() || pane == 1
									&& !check2.isSelected()) {
								s = s.toLowerCase();
								search = search.toLowerCase();
							}

							if (currentLine == startLine) {
								canSearch = true;
							} else if (s.contains(search)) {
								if (canSearch) {
									area.setCaretPosition(area.getDocument()
											.getDefaultRootElement()
											.getElement(currentLine - 1)
											.getStartOffset());
									canSearch = false;
									found = true;
								}

								if (firstPos == -1)
									firstPos = currentLine;
							}

							currentLine++;
						}

						if (!found && firstPos != -1) {
							area.setCaretPosition(area.getDocument()
									.getDefaultRootElement()
									.getElement(firstPos - 1).getStartOffset());
						}
					} else {
						canSearch = true;
						for (String s : test) {
							if (pane == 0 && !check1.isSelected() || pane == 1
									&& !check2.isSelected() || pane == 2
									&& !check3.isSelected()) {
								s = s.toLowerCase();
								search = search.toLowerCase();
							}

							if (s.contains(search)) {
								if (lastGoodLine != -1 && canSearch)
									area.setCaretPosition(area.getDocument()
											.getDefaultRootElement()
											.getElement(lastGoodLine - 1)
											.getStartOffset());

								lastGoodLine = currentLine;

								if (currentLine >= startLine)
									canSearch = false;
							}
							currentLine++;
						}

						if (lastGoodLine != -1
								&& area.getDocument()
										.getDefaultRootElement()
										.getElementIndex(
												area.getCaretPosition()) + 1 == startLine) {
							area.setCaretPosition(area.getDocument()
									.getDefaultRootElement()
									.getElement(lastGoodLine - 1)
									.getStartOffset());
						}
					}
					highlight(pane, area, search);
				}
			}
		} catch (Exception e) {
			new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
		}
	}

	private DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
			new Color(255, 62, 150));

	public void highlight(int pane, JTextComponent textComp, String pattern) {
		if (pattern.isEmpty()) {
			textComp.getHighlighter().removeAllHighlights();
			return;
		}

		try {
			Highlighter hilite = textComp.getHighlighter();
			hilite.removeAllHighlights();
			javax.swing.text.Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			if ((pane == 0 && !check1.isSelected()) || pane == 1
					&& !check2.isSelected() || pane == 2
					&& !check3.isSelected()) {
				pattern = pattern.toLowerCase();
				text = text.toLowerCase();
			}

			// Search for pattern
			while ((pos = text.indexOf(pattern, pos)) >= 0) {
				// Create highlighter using private painter and apply around
				// pattern
				hilite.addHighlight(pos, pos + pattern.length(), painter);
				pos += pattern.length();
			}
		} catch (Exception e) {
			new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
		}
	}

	public ClassViewer(final String name, final ClassNode cn) {
		JButton byteSearchNext = new JButton();
		JButton byteSearchPrev = new JButton();
		JPanel byteButtonPane = new JPanel(new BorderLayout());
		byteButtonPane.add(byteSearchNext, BorderLayout.WEST);
		byteButtonPane.add(byteSearchPrev, BorderLayout.EAST);
		byteSearchNext
				.setIcon(new ImageIcon(
						BytecodeViewer
								.b642IMG("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAAMFBMVEX///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAv3aB7AAAABnRSTlMANzlYqPBJSG/ZAAAASUlEQVR42mNgwAbS0oAEE4yHyWBmYAzjYDC694OJ4f9+BoY3H0BSbz6A2MxA6VciFyDqGAWQTWVkYEkCUrcOsDD8OwtkvMViMwAb8xEUHlHcFAAAAABJRU5ErkJggg==")));
		byteSearchPrev
				.setIcon(new ImageIcon(
						BytecodeViewer
								.b642IMG("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAAMFBMVEX///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAv3aB7AAAABnRSTlMANzlYgKhxpRi1AAAATElEQVR42mNgwAZYHIAEExA7qUAYLApMDmCGEwODCojByM/A8FEAyPi/moFh9QewYjCAM1iA+D2KqYwMrIlA6tUGFoa/Z4GMt1hsBgCe1wuKber+SwAAAABJRU5ErkJggg==")));
		panel1Search.add(byteButtonPane, BorderLayout.WEST);
		final JTextField field1 = new JTextField();
		panel1Search.add(field1, BorderLayout.CENTER);
		panel1Search.add(check1, BorderLayout.EAST);
		byteSearchNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				search(0, field1.getText(), true);
			}
		});
		byteSearchPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				search(0, field1.getText(), false);
			}
		});
		field1.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
					search(0, field1.getText(), true);
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		JButton searchNext2 = new JButton();
		JButton searchPrev2 = new JButton();
		JPanel buttonPane2 = new JPanel(new BorderLayout());
		buttonPane2.add(searchNext2, BorderLayout.WEST);
		buttonPane2.add(searchPrev2, BorderLayout.EAST);
		searchNext2
				.setIcon(new ImageIcon(
						BytecodeViewer
								.b642IMG("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAAMFBMVEX///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAv3aB7AAAABnRSTlMANzlYqPBJSG/ZAAAASUlEQVR42mNgwAbS0oAEE4yHyWBmYAzjYDC694OJ4f9+BoY3H0BSbz6A2MxA6VciFyDqGAWQTWVkYEkCUrcOsDD8OwtkvMViMwAb8xEUHlHcFAAAAABJRU5ErkJggg==")));
		searchPrev2
				.setIcon(new ImageIcon(
						BytecodeViewer
								.b642IMG("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAAMFBMVEX///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAv3aB7AAAABnRSTlMANzlYgKhxpRi1AAAATElEQVR42mNgwAZYHIAEExA7qUAYLApMDmCGEwODCojByM/A8FEAyPi/moFh9QewYjCAM1iA+D2KqYwMrIlA6tUGFoa/Z4GMt1hsBgCe1wuKber+SwAAAABJRU5ErkJggg==")));
		panel2Search.add(buttonPane2, BorderLayout.WEST);
		final JTextField field2 = new JTextField();
		panel2Search.add(field2, BorderLayout.CENTER);
		panel2Search.add(check2, BorderLayout.EAST);
		searchNext2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				search(1, field2.getText(), true);
			}
		});
		searchPrev2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				search(1, field2.getText(), false);
			}
		});
		field2.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
					search(1, field2.getText(), true);
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		JButton searchNext3 = new JButton();
		JButton searchPrev3 = new JButton();
		JPanel buttonPane3 = new JPanel(new BorderLayout());
		buttonPane3.add(searchNext3, BorderLayout.WEST);
		buttonPane3.add(searchPrev3, BorderLayout.EAST);
		searchNext3
				.setIcon(new ImageIcon(
						BytecodeViewer
								.b642IMG("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAAMFBMVEX///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAv3aB7AAAABnRSTlMANzlYqPBJSG/ZAAAASUlEQVR42mNgwAbS0oAEE4yHyWBmYAzjYDC694OJ4f9+BoY3H0BSbz6A2MxA6VciFyDqGAWQTWVkYEkCUrcOsDD8OwtkvMViMwAb8xEUHlHcFAAAAABJRU5ErkJggg==")));
		searchPrev3
				.setIcon(new ImageIcon(
						BytecodeViewer
								.b642IMG("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAAMFBMVEX///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAv3aB7AAAABnRSTlMANzlYgKhxpRi1AAAATElEQVR42mNgwAZYHIAEExA7qUAYLApMDmCGEwODCojByM/A8FEAyPi/moFh9QewYjCAM1iA+D2KqYwMrIlA6tUGFoa/Z4GMt1hsBgCe1wuKber+SwAAAABJRU5ErkJggg==")));
		panel3Search.add(buttonPane3, BorderLayout.WEST);
		final JTextField field3 = new JTextField();
		panel3Search.add(field3, BorderLayout.CENTER);
		panel3Search.add(check3, BorderLayout.EAST);
		searchNext3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				search(2, field3.getText(), true);
			}
		});
		searchPrev3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				search(2, field3.getText(), false);
			}
		});
		field3.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
					search(2, field3.getText(), true);
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		this.name = name;
		this.cn = cn;
		this.setName(name);
		this.setLayout(new BorderLayout());

		this.sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);
		final ClassWriter cw = new ClassWriter(0);
		cn.accept(cw);
		JHexEditor hex = new JHexEditor(cw.toByteArray());
		this.sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, panel3);
		this.add(sp2, BorderLayout.CENTER);

		hex.setMaximumSize(new Dimension(0, Integer.MAX_VALUE));
		hex.setSize(0, Integer.MAX_VALUE);

		BytecodeViewer.viewer.setIcon(true);
		startPaneUpdater(null);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resetDivider();
			}
		});
	}

	public void resetDivider() {
		sp.setResizeWeight(0.5);
		if (pane2 != 0 && pane1 != 0)
			sp = setDividerLocation(sp, 0.5);
		else if (pane1 != 0)
			sp = setDividerLocation(sp, 1);
		else
			sp = setDividerLocation(sp, 0);
		if (pane3 != 0) {
			sp2.setResizeWeight(0.7);
			sp2 = setDividerLocation(sp2, 0.7);
			if ((pane2 == 0 && pane1 != 0) || (pane1 == 0 && pane2 != 0))
				sp2 = setDividerLocation(sp2, 0.5);
			else if (pane1 == 0 && pane2 == 0)
				sp2 = setDividerLocation(sp2, 0);
		} else {
			sp2.setResizeWeight(0);
			sp2 = setDividerLocation(sp2, 1);
		}
	}

	static FernFlowerDecompiler ff_dc = new FernFlowerDecompiler();
	static ProcyonDecompiler proc_dc = new ProcyonDecompiler();
	static CFRDecompiler cfr_dc = new CFRDecompiler();
	PaneUpdaterThread t;

	public void startPaneUpdater(final JButton button) {
		if (BytecodeViewer.viewer.decompilerGroup1
				.isSelected(BytecodeViewer.viewer.panel1None.getModel()))
			pane1 = 0;
		else if (BytecodeViewer.viewer.decompilerGroup1
				.isSelected(BytecodeViewer.viewer.panel1Proc.getModel()))
			pane1 = 1;
		else if (BytecodeViewer.viewer.decompilerGroup1
				.isSelected(BytecodeViewer.viewer.panel1CFR.getModel()))
			pane1 = 2;
		else if (BytecodeViewer.viewer.decompilerGroup1
				.isSelected(BytecodeViewer.viewer.panel1Fern.getModel()))
			pane1 = 3;
		else if (BytecodeViewer.viewer.decompilerGroup1
				.isSelected(BytecodeViewer.viewer.panel1Bytecode.getModel()))
			pane1 = 4;
		else if (BytecodeViewer.viewer.decompilerGroup1
				.isSelected(BytecodeViewer.viewer.panel1Hexcode.getModel()))
			pane1 = 5;

		if (BytecodeViewer.viewer.decompilerGroup2
				.isSelected(BytecodeViewer.viewer.panel2None.getModel()))
			pane2 = 0;
		else if (BytecodeViewer.viewer.decompilerGroup2
				.isSelected(BytecodeViewer.viewer.panel2Proc.getModel()))
			pane2 = 1;
		else if (BytecodeViewer.viewer.decompilerGroup2
				.isSelected(BytecodeViewer.viewer.panel2CFR.getModel()))
			pane2 = 2;
		else if (BytecodeViewer.viewer.decompilerGroup2
				.isSelected(BytecodeViewer.viewer.panel2Fern.getModel()))
			pane2 = 3;
		else if (BytecodeViewer.viewer.decompilerGroup2
				.isSelected(BytecodeViewer.viewer.panel2Bytecode.getModel()))
			pane2 = 4;
		else if (BytecodeViewer.viewer.decompilerGroup2
				.isSelected(BytecodeViewer.viewer.panel2Hexcode.getModel()))
			pane2 = 5;

		if (BytecodeViewer.viewer.decompilerGroup3
				.isSelected(BytecodeViewer.viewer.panel3None.getModel()))
			pane3 = 0;
		else if (BytecodeViewer.viewer.decompilerGroup3
				.isSelected(BytecodeViewer.viewer.panel3Proc.getModel()))
			pane3 = 1;
		else if (BytecodeViewer.viewer.decompilerGroup3
				.isSelected(BytecodeViewer.viewer.panel3CFR.getModel()))
			pane3 = 2;
		else if (BytecodeViewer.viewer.decompilerGroup3
				.isSelected(BytecodeViewer.viewer.panel3Fern.getModel()))
			pane3 = 3;
		else if (BytecodeViewer.viewer.decompilerGroup3
				.isSelected(BytecodeViewer.viewer.panel3Bytecode.getModel()))
			pane3 = 4;
		else if (BytecodeViewer.viewer.decompilerGroup3
				.isSelected(BytecodeViewer.viewer.panel3Hexcode.getModel()))
			pane3 = 5;

		t = new PaneUpdaterThread() {
			@Override
			public void doShit() {
				try {
					panel1.removeAll();
					panel2.removeAll();
					panel3.removeAll();
	
					if (pane1 != 0 && pane1 != 5)
						panel1.add(panel1Search, BorderLayout.NORTH);
					if (pane2 != 0 && pane2 != 5)
						panel2.add(panel2Search, BorderLayout.NORTH);
					if (pane3 != 0 && pane3 != 5)
						panel3.add(panel3Search, BorderLayout.NORTH);
	
					if (pane1 == 1) { // procyon
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(proc_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel1.add(scrollPane);
					}
	
					if (pane1 == 2) {// cfr
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(cfr_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel1.add(scrollPane);
					}
	
					if (pane1 == 3) {// fern
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(ff_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel1.add(scrollPane);
					}
	
					if (pane1 == 4) {// bytecode
						RSyntaxTextArea bytecodeArea = new RSyntaxTextArea();
						bytecodeArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						bytecodeArea.setCodeFoldingEnabled(true);
						bytecodeArea.setAntiAliasingEnabled(true);
						RTextScrollPane bytecodeSPane = new RTextScrollPane(
								bytecodeArea);
						bytecodeArea.setText(ClassNodeDecompiler.decompile(cn));
						bytecodeArea.setCaretPosition(0);
						panel1.add(bytecodeSPane);
					}
	
					if (pane1 == 5) {// hex
						final ClassWriter cw = new ClassWriter(0);
						cn.accept(cw);
						JHexEditor hex = new JHexEditor(cw.toByteArray());
						panel1.add(hex);
					}
	
					if (pane2 == 1) {
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(proc_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel2.add(scrollPane);
					}
	
					if (pane2 == 2) {
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(cfr_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel2.add(scrollPane);
					}
	
					if (pane2 == 3) {
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(ff_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel2.add(scrollPane);
					}
	
					if (pane2 == 4) {
						RSyntaxTextArea paneArea = new RSyntaxTextArea();
						paneArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						paneArea.setCodeFoldingEnabled(true);
						paneArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(paneArea);
						paneArea.setText(ClassNodeDecompiler.decompile(cn));
						paneArea.setCaretPosition(0);
						panel2.add(scrollPane);
					}
	
					if (pane2 == 5) {
						final ClassWriter cw = new ClassWriter(0);
						cn.accept(cw);
						JHexEditor hex = new JHexEditor(cw.toByteArray());
						panel2.add(hex);
					}
	
					if (pane3 == 1) {
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(proc_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel3.add(scrollPane);
					}
	
					if (pane3 == 2) {
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(cfr_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel3.add(scrollPane);
					}
	
					if (pane3 == 3) {
						RSyntaxTextArea panelArea = new RSyntaxTextArea();
						panelArea
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						panelArea.setCodeFoldingEnabled(true);
						panelArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(panelArea);
						panelArea.setText(ff_dc.decompileClassNode(cn));
						panelArea.setCaretPosition(0);
						panel3.add(scrollPane);
					}
	
					if (pane3 == 4) {
						RSyntaxTextArea paneArea = new RSyntaxTextArea();
						paneArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						paneArea.setCodeFoldingEnabled(true);
						paneArea.setAntiAliasingEnabled(true);
						RTextScrollPane scrollPane = new RTextScrollPane(paneArea);
						paneArea.setText(ClassNodeDecompiler.decompile(cn));
						paneArea.setCaretPosition(0);
						panel3.add(scrollPane);
					}
	
					if (pane3 == 5) {
						final ClassWriter cw = new ClassWriter(0);
						cn.accept(cw);
						JHexEditor hex = new JHexEditor(cw.toByteArray());
						panel3.add(hex);
					}
	
					resetDivider();
					BytecodeViewer.viewer.setIcon(false);
				} catch(Exception e) {
					new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
				} finally {
					if(button != null)
						button.setEnabled(true);
				}
			}

		};
		t.start();
	}

	public static class MethodData {
		public String name, desc;
		public int srcLN, bytecodeLN;

		@Override
		public boolean equals(final Object o) {
			return equals((MethodData) o);
		}

		public boolean equals(final MethodData md) {
			return this.name.equals(md.name) && this.desc.equals(md.desc);
		}

		public String constructPattern() {
			final StringBuffer pattern = new StringBuffer();
			pattern.append(name + " *\\(");
			final org.objectweb.asm.Type[] types = org.objectweb.asm.Type
					.getArgumentTypes(desc);
			pattern.append("(.*)");
			for (int i = 0; i < types.length; i++) {
				final Type type = types[i];
				final String clazzName = type.getClassName();
				pattern.append(clazzName.substring(clazzName.lastIndexOf(".") + 1)
						+ "(.*)");
			}
			pattern.append("\\) *\\{");
			return pattern.toString();
		}
	}

	class WrapEditorKit extends StyledEditorKit {
		private static final long serialVersionUID = 1719109651258205346L;
		ViewFactory defaultFactory = new WrapColumnFactory();

		@Override
		public ViewFactory getViewFactory() {
			return defaultFactory;
		}
	}

	class WrapColumnFactory implements ViewFactory {
		public View create(final Element elem) {
			final String kind = elem.getName();
			if (kind != null) {
				if (kind.equals(AbstractDocument.ParagraphElementName))
					return new NoWrapParagraphView(elem);
				else if (kind.equals(AbstractDocument.SectionElementName))
					return new BoxView(elem, View.Y_AXIS);
				else if (kind.equals(StyleConstants.ComponentElementName))
					return new ComponentView(elem);
				else if (kind.equals(StyleConstants.IconElementName))
					return new IconView(elem);
			}

			return new LabelView(elem);
		}
	}

	public class NoWrapParagraphView extends ParagraphView {
		public NoWrapParagraphView(final Element elem) {
			super(elem);
		}

		@Override
		public void layout(final int width, final int height) {
			super.layout(Short.MAX_VALUE, height);
		}

		@Override
		public float getMinimumSpan(final int axis) {
			return super.getPreferredSpan(axis);
		}
	}

}
