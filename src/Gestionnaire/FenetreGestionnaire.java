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
import java.text.NumberFormat;

import static java.lang.Boolean.TRUE;

import javax.swing.*;
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
    public boolean isSelected = false;
    public String selectedObjectName = "";

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

        DefaultListModel<String> l1 = new DefaultListModel<>();
        JList<String> list = new JList<>(l1);
        list.setBounds(100  , 100, 100, 100);
        p.add(list);
//        list.addKeyListener(new KeyAdapter() {
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    // String  text =  e.getSelectedValue().toString();
//
//                }
//            }
//        });

        // Creating a panel specified to contain labels
        JPanel labelsPanel = new JPanel();

        // Setting labelsPanel Layout to be a vertical box
        BoxLayout boxLayout = new BoxLayout(labelsPanel,BoxLayout.Y_AXIS);
        labelsPanel.setLayout(boxLayout);

        // Details Label definition && making a space which height = 15px
        JLabel details = new JLabel("Details article menu ");
        labelsPanel.add(details);
        labelsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Name Label definition && making a space which height = 15px
        JLabel name = new JLabel("Nom :");
        labelsPanel.add(name);
        labelsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Tarif Label definition && making a space which height = 15px
        JLabel tarif = new JLabel("Tarif :");
        labelsPanel.add(tarif);
        labelsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Article Label definition && making a space which height = 5 because it's the last element
        JLabel article = new JLabel("Article :");
        labelsPanel.add(article);
        labelsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Creating a panel specified to contain quantify label and quantity textfield
        JPanel quantPanel = new JPanel();

        // Setting quantPanel Layout to be a horizantal box
        quantPanel.setLayout(new BoxLayout(quantPanel, BoxLayout.X_AXIS));

        // Creating quantity label and adding it to quantPanel && making some horizantal space = 15
        String label = "Quantite: ";
        JLabel l = new JLabel(label, JLabel.HORIZONTAL);
        quantPanel.add(l);
        labelsPanel.add(Box.createRigidArea(new Dimension(15, 10)));

        // Creating a new textfield to contain quantity input and attaching it to quantLabel
        JTextField textField = new JTextField(10);
        l.setLabelFor(textField);
        textField.setMaximumSize(new Dimension(300,
                30));
        quantPanel.add(textField);

        // Ensure that quantPanel components are attached to LEFT
        quantPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Creating a new vertical box layout to contain the other two boxes
        BoxLayout bigBoxLayout = new BoxLayout(p3,BoxLayout.Y_AXIS);
        p3.setLayout(bigBoxLayout);

        // Adding the two boxes to the main p3 panel
        p3.add(labelsPanel);
        p3.add(quantPanel);

        // Adding the add button layout
        JButton addButton = new JButton();
        addButton.setText("Ajouter");
        p3.add(Box.createRigidArea(new Dimension(0, 10)));

        // Setting the add button action
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSelected && textField.getText() != null && textField.getText() != "" && !textField.getText().isBlank() ) {
                    try {
                        double quantity = Double.parseDouble(textField.getText());
                        l1.addElement(textField.getText() + " * " + selectedObjectName);
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("Number Convertion exception raised: " + numberFormatException);
                        numberFormatException.printStackTrace();
                    }
                }
            }
        });

        p3.add(addButton);


        tree = new JTree(this.read());
        add(new JScrollPane(tree));
        add(p3);
        add(p);
        add(p1);
        tree.setVisible(TRUE);
        setSize(2250, 750);
        setResizable(true);
        setLocationRelativeTo(null);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }

                isSelected = false;

                Object path = e.getPath().getLastPathComponent();
                if (node.isLeaf()) {
                    selectedObjectName = path.toString();
                    article.setText("Article : " + selectedObjectName);
                    isSelected = true;
                } else {
                    name.setText("Nom : " + path.toString());
                    article.setText("Article : ");
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
        DefaultMutableTreeNode article = null;     // Level 1 in Hierarchy

//        File file = new File(pathToFile);
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null); //replace null with your swing container
        File file;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String strLine;
            String[] result;

            while ((strLine = buffer.readLine()) != null) {
                result = strLine.split(":");
                for (int i = 0; i < result.length; i++) {
                    if (i == 0) {
                        article = new DefaultMutableTreeNode(result[i]);
                    } else {
                        article.add(new DefaultMutableTreeNode(result[i]));
                    }
                    menu.add(article);
                }
            }
            buffer.close();
            return (menu);
        }
        else
        {
            return new DefaultMutableTreeNode();
        }
    }
}
