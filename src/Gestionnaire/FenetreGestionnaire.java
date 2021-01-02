package Gestionnaire;

/* IMPORTS*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Boolean.TRUE;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Ghazi Tozri
 */
public final class FenetreGestionnaire extends JFrame implements ActionListener {

    public JMenu menu;
    public JMenu menu2;

    public JTree tree;
    public String L;
    public String st;
    public JMenuItem i1, i2, i3, i4, i5, i6;
    public DefaultMutableTreeNode root;

    public FenetreGestionnaire() throws IOException {
        JMenuBar mb = new JMenuBar();
        menu = new JMenu("Fichier");
        menu2 = new JMenu("Article");
        mb.add(menu);
        mb.add(menu2);
        add(mb);
        i1 = new JMenuItem("Explorer");
        i2 = new JMenuItem("Importer");
        i3 = new JMenuItem("Quitter");
        i4 = new JMenuItem("Nouveau");
        i5 = new JMenuItem("Modifier");
        i6 = new JMenuItem("Supprimer");
        menu.add(i1);
        menu.add(i2);
        menu.add(i3);
        menu2.add(i4);
        menu2.add(i5);
        menu2.add(i6);
        //  menu.add(submenu);
        JPanel p1 = new JPanel();
        JPanel p = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        setVisible(true);
        setJMenuBar(mb);
        setLayout(new GridLayout(2, 2));
        setVisible(true);

        //add(p2) ;

        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);
        i4.addActionListener(this);
        i5.addActionListener(this);
        i6.addActionListener(this);
        JLabel j = new JLabel("Details article menu ");
        j.setBounds(100,100,100,100);
        JLabel j1 = new JLabel("Nom :");
        JLabel j2 = new JLabel("Tarif :");
        JLabel j3 = new JLabel("Article :");
        JTextField F1 = new JTextField();
        F1.setBounds(100, 100, 100, 100);
        p3.add(j);
        p3.add(j1);
        p3.add(j2);
        p3.add(j3);

        p3.setLayout(new GridLayout(4, 4));

        tree = new JTree(this.read());
        add(new JScrollPane(tree));
        add(p3);
        add(p);
        add(p1);
        tree.setVisible(TRUE);
        setBounds(500, 500,500,500);
        setSize(200, 200);
        setResizable(true);
        setLocationRelativeTo(null);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                Object nodeInfo = node.getUserObject();
                if (node.isLeaf()) {
                    System.out.println("feuille");
                    System.out.println(e.getPath().toString());
                } else {
                    System.out.println("noeud");
                    System.out.println(e.getPath().toString());
                }
            }
        });
        DefaultListModel<String> l1 = new DefaultListModel<>();
        l1.addElement("Item1");
        l1.addElement("Item2");
        l1.addElement("Item3");
        l1.addElement("Item4");
        JList<String> list = new JList<>(l1);
        list.setBounds(100  , 100, 100, 100);
        p.add(list);
        list.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // String  text =  e.getSelectedValue().toString();

                }
            }
        });
        //add(new JScrollPane(tree));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JTree Example");
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().toString() == "Supprimer") {
            DefaultTreeModel ttmodel = (DefaultTreeModel) this.tree.getModel();

            //ttmodel.setRoot(null);
            DefaultMutableTreeNode troot = (DefaultMutableTreeNode) ttmodel.getRoot();
            troot.removeAllChildren();
            ttmodel.reload();
        }
       if (e.getActionCommand().toString() == "Importer") {
            System.out.println("Importer");
        }

        System.out.println(e.getActionCommand().toString());
    }

    public DefaultMutableTreeNode read() throws IOException {
        DefaultMutableTreeNode menu = new DefaultMutableTreeNode("menu");
        DefaultMutableTreeNode boisson = null;     // Level 1 in Hierarchy
        DefaultMutableTreeNode boissonfroid = null;  // Level 2 in Hierarchy
        DefaultMutableTreeNode sandwish = null;
        DefaultMutableTreeNode plat = null;

        File file = new File("/Users/mohamedalibelhadj/Desktop/drinks.txt");

        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String strLine;
        String[] result;

        while ((strLine = buffer.readLine()) != null)   {
            result =  strLine.split(":");
            for(int i = 0; i < result.length; i++) {
                if (i == 0)
                {
                    boisson = new DefaultMutableTreeNode(result[i]);
                }
                else
                {
                    boisson.add(new DefaultMutableTreeNode(result[i]));
                }
                menu.add(boisson) ;
            }
        }

        buffer.close();
        return (menu);
    }
}