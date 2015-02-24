package the.bytecode.club.bytecodeviewer.gui;

import java.awt.BorderLayout;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.objectweb.asm.tree.ClassNode;

import the.bytecode.club.bytecodeviewer.*;

/**
 * The file navigation pane.
 * 
 * @author Konloch
 * @author WaterWolf
 *
 */

@SuppressWarnings("serial")
public class FileNavigationPane extends VisibleComponent implements
		FileDrop.Listener {

	FileChangeNotifier fcn;
	JCheckBox exact = new JCheckBox("Exact");
	JButton open = new JButton("+");
	JButton close = new JButton("-");

	MyTreeNode treeRoot = new MyTreeNode("Root");
	MyTree tree;

	public FileNavigationPane(final FileChangeNotifier fcn) {
		super("ClassNavigation");
		setTitle("Files");

		this.fcn = fcn;
		
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final TreeNode root = (TreeNode) tree.getModel().getRoot();
				expandAll(tree, new TreePath(root), true);
			}
			
		});
		
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final TreeNode root = (TreeNode) tree.getModel().getRoot();
				expandAll(tree, new TreePath(root), false);
				tree.expandPath(new TreePath(root));
			}
			
		});

		getContentPane().setLayout(new BorderLayout());

		this.tree = new MyTree(treeRoot);
		getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
		
		MouseAdapter ml = new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if (path == null)
					return;
				final StringBuffer nameBuffer = new StringBuffer();
				for (int i = 1; i < path.getPathCount(); i++) {
					nameBuffer.append(path.getPathComponent(i));
					if (i < path.getPathCount() - 1) {
						nameBuffer.append("/");
					}
				}
				
				String name = nameBuffer.toString();
				if(name.endsWith(".class")) {
					final ClassNode cn = BytecodeViewer.getClassNode(name.substring(0, name.length() - ".class".length()));
					if (cn != null) {
						openClassFileToWorkSpace(nameBuffer.toString(), cn);
					}
				} else {
					openFileToWorkSpace(nameBuffer.toString(), BytecodeViewer.getFileContents(nameBuffer.toString()));
				}
		    }
		};
		this.tree.addMouseListener(ml);

		this.tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(final TreeSelectionEvent arg0) {
				final TreePath path = arg0.getPath();
				if (((TreeNode) path.getLastPathComponent()).getChildCount() > 0)
					return;
				final StringBuffer nameBuffer = new StringBuffer();
				for (int i = 1; i < path.getPathCount(); i++) {
					nameBuffer.append(path.getPathComponent(i));
					if (i < path.getPathCount() - 1) {
						nameBuffer.append("/");
					}
				}
				
				String name = nameBuffer.toString();
				if(name.endsWith(".class")) {
					final ClassNode cn = BytecodeViewer.getClassNode(name.substring(0, name.length() - ".class".length()));
					if (cn != null) {
						openClassFileToWorkSpace(nameBuffer.toString(), cn);
					}
				} else {
					openFileToWorkSpace(nameBuffer.toString(), BytecodeViewer.getFileContents(nameBuffer.toString()));
				}
			}
		});
		
		final String quickSearchText = "Quick class search (no file extension)";

		final JTextField quickSearch = new JTextField(quickSearchText);
		quickSearch.setForeground(Color.gray);
		quickSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {

					final String qt = quickSearch.getText();
					quickSearch.setText("");

					
					String[] path = null;

					if (qt.contains(".")) {
						path = qt.split("\\.");
						String[] path2 = new String[path.length];
						for(int i = 0; i < path.length; i++) {
							path2[i] = path[i];
							if(i+2 == path.length) {
								path2[i+1] = "." + path[i+1];
							}
						}
					} else {
						path = new String[] { qt };
					}

					MyTreeNode curNode = treeRoot;
					if (exact.isSelected()) {
						pathLoop: for (int i = 0; i < path.length; i++) {
							final String pathName = path[i];
							final boolean isLast = i == path.length - 1;

							for (int c = 0; c < curNode.getChildCount(); c++) {
								final MyTreeNode child = (MyTreeNode) curNode.getChildAt(c);
								System.out.println(pathName +":"+((String) child.getUserObject()));
								
								if (((String) child.getUserObject()).equals(pathName)) {
									curNode = child;
									if (isLast) {
										final TreePath pathn = new TreePath(curNode.getPath());
										tree.setSelectionPath(pathn);
										tree.makeVisible(pathn);
										tree.scrollPathToVisible(pathn);
										System.out.println("Found! " + curNode);
										break pathLoop;
									}
									continue pathLoop;
								}
							}

							System.out.println("Could not find " + pathName);
							break;
						}
					} else {
						{
							@SuppressWarnings("unchecked")
							Enumeration<MyTreeNode> enums = curNode.depthFirstEnumeration();
							while (enums != null && enums.hasMoreElements()) {
	
								MyTreeNode node = enums.nextElement();
								if (node.isLeaf()) {
									if (((String) (node.getUserObject())).toLowerCase().contains(path[path.length - 1].toLowerCase())) {
										TreeNode pathArray[] = node.getPath();
										int k = 0;
										StringBuffer fullPath = new StringBuffer();
										while (pathArray != null
												&& k < pathArray.length) {
											MyTreeNode n = (MyTreeNode) pathArray[k];
											String s = (String) (n.getUserObject());
											fullPath.append(s);
											if (k++ != pathArray.length - 1) {
												fullPath.append(".");
											}
										}
										String fullPathString = fullPath.toString();
										if (fullPathString != null && fullPathString.toLowerCase().contains(qt.toLowerCase())) {
											System.out.println("Found! " + node);
											final TreePath pathn = new TreePath(node.getPath());
											tree.setSelectionPath(pathn.getParentPath());
											tree.setSelectionPath(pathn);
											tree.makeVisible(pathn);
											tree.scrollPathToVisible(pathn);
										}
									}
								}
							}
						}
					}

				}
			}
		});
		quickSearch.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(final FocusEvent arg0) {
				if (quickSearch.getText().equals(quickSearchText)) {
					quickSearch.setText("");
					quickSearch.setForeground(Color.black);
				}
			}

			@Override
			public void focusLost(final FocusEvent arg0) {
				if (quickSearch.getText().isEmpty()) {
					quickSearch.setText(quickSearchText);
					quickSearch.setForeground(Color.gray);
				}
			}
		});

		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(quickSearch, BorderLayout.NORTH);
		JPanel p3 = new JPanel(new BorderLayout());
		p3.add(exact, BorderLayout.WEST);
		JPanel p4 = new JPanel(new BorderLayout());
		p4.add(open, BorderLayout.EAST);
		p4.add(close, BorderLayout.WEST);
		p3.add(p4, BorderLayout.EAST);
		p2.add(p3, BorderLayout.SOUTH);

		getContentPane().add(p2, BorderLayout.SOUTH);

		this.setVisible(true);
		new FileDrop(this, this);
	}

	public void openClassFileToWorkSpace(final String name, final ClassNode node) {
		fcn.openClassFile(name, node);
	}
	
	public void openFileToWorkSpace(String name, byte[] contents) {
		fcn.openFile(name, contents);
	}

	@Override
	public void filesDropped(final File[] files) {
		if (files.length < 1)
			return;
		BytecodeViewer.openFiles(files, true);
	}

	public void updateTree() {
		try {
			treeRoot.removeAllChildren();
			for (final Entry<String, ClassNode> entry : BytecodeViewer.loadedClasses
					.entrySet()) {
				String name = entry.getKey();
				final String[] spl = name.split("/");
				if (spl.length < 2) {
					treeRoot.add(new MyTreeNode(name+".class"));
				} else {
					MyTreeNode parent = treeRoot;
					for (int i1 = 0; i1 < spl.length; i1++) {
						String s = spl[i1];
						MyTreeNode child = null;
						for (int i = 0; i < parent.getChildCount(); i++) {
							if (((MyTreeNode) parent.getChildAt(i)).getUserObject()
									.equals(s)) {
								child = (MyTreeNode) parent.getChildAt(i);
								break;
							}
						}
						if (child == null) {
							if(i1 == spl.length-1)
								child = new MyTreeNode(s+".class");
							else
								child = new MyTreeNode(s);
							parent.add(child);
						}
						parent = child;
					}
				}
			}
			
			for (final Entry<String, byte[]> entry : BytecodeViewer.loadedResources.entrySet()) {
				String name = entry.getKey();
				final String[] spl = name.split("/");
				if (spl.length < 2) {
					treeRoot.add(new MyTreeNode(name));
				} else {
					MyTreeNode parent = treeRoot;
					for (final String s : spl) {
						MyTreeNode child = null;
						for (int i = 0; i < parent.getChildCount(); i++) {
							if (((MyTreeNode) parent.getChildAt(i)).getUserObject()
									.equals(s)) {
								child = (MyTreeNode) parent.getChildAt(i);
								break;
							}
						}
						if (child == null) {
							child = new MyTreeNode(s);
							parent.add(child);
						}
						parent = child;
					}
				}
			}
	
			treeRoot.sort();
			tree.expandPath(new TreePath(tree.getModel().getRoot()));
			tree.updateUI();
		} catch(java.util.ConcurrentModificationException e) {
			//ignore, the last file will reset everything
		}
		// expandAll(tree, true);
	}

	@SuppressWarnings("rawtypes")
	private void expandAll(final JTree tree, final TreePath parent,
			final boolean expand) {
		// Traverse children
		final TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (final Enumeration e = node.children(); e.hasMoreElements();) {
				final TreeNode n = (TreeNode) e.nextElement();
				final TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}

		// Expansion or collapse must be done bottom-up
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	public class MyTree extends JTree {

		private static final long serialVersionUID = -2355167326094772096L;
		DefaultMutableTreeNode treeRoot;

		public MyTree(final DefaultMutableTreeNode treeRoot) {
			super(treeRoot);
			this.treeRoot = treeRoot;
		}

		StringMetrics m = null;

		@Override
		public void paint(final Graphics g) {
			try {
				super.paint(g);
				if (m == null) {
					m = new StringMetrics((Graphics2D) g);
				}
				if (treeRoot.getChildCount() < 1) {
					g.setColor(new Color(0, 0, 0, 100));
					g.fillRect(0, 0, getWidth(), getHeight());
					g.setColor(Color.white);
					String s = "Drag class/jar here";
					g.drawString(s,
							((int) ((getWidth() / 2) - (m.getWidth(s) / 2))),
							getHeight() / 2);
				}
			} catch(java.lang.InternalError | java.lang.NullPointerException e) {
				
			}
		}
	}

	public class MyTreeNode extends DefaultMutableTreeNode {

		private static final long serialVersionUID = -8817777566176729571L;

		public MyTreeNode(final Object o) {
			super(o);
		}

		@Override
		public void insert(final MutableTreeNode newChild, final int childIndex) {
			super.insert(newChild, childIndex);
		}

		public void sort() {
			recursiveSort(this);
		}

		@SuppressWarnings("unchecked")
		private void recursiveSort(final MyTreeNode node) {
			Collections.sort(node.children, nodeComparator);
			final Iterator<MyTreeNode> it = node.children.iterator();
			while (it.hasNext()) {
				final MyTreeNode nextNode = it.next();
				if (nextNode.getChildCount() > 0) {
					recursiveSort(nextNode);
				}
			}
		}

		protected Comparator<MyTreeNode> nodeComparator = new Comparator<MyTreeNode>() {
			@Override
			public int compare(final MyTreeNode o1, final MyTreeNode o2) {
				// To make sure nodes with children are always on top
				final int firstOffset = o1.getChildCount() > 0 ? -1000 : 0;
				final int secondOffset = o2.getChildCount() > 0 ? 1000 : 0;
				return o1.toString().compareToIgnoreCase(o2.toString())
						+ firstOffset + secondOffset;
			}

			@Override
			public boolean equals(final Object obj) {
				return false;
			}

			@Override
			public int hashCode() {
				final int hash = 7;
				return hash;
			}
		};
	}

	/**
	 * 
	 * @author http://stackoverflow.com/a/18450804
	 * 
	 */
	class StringMetrics {

		Font font;
		FontRenderContext context;

		public StringMetrics(Graphics2D g2) {

			font = g2.getFont();
			context = g2.getFontRenderContext();
		}

		Rectangle2D getBounds(String message) {

			return font.getStringBounds(message, context);
		}

		double getWidth(String message) {

			Rectangle2D bounds = getBounds(message);
			return bounds.getWidth();
		}

		double getHeight(String message) {

			Rectangle2D bounds = getBounds(message);
			return bounds.getHeight();
		}

	}

	public void resetWorkspace() {
		treeRoot.removeAllChildren();
		tree.repaint();
		tree.updateUI();
	}

}
