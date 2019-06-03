package oop;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
import java.io.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class World 
{
    World(Vec2 size)
    {
        reset(size);
        
        KeyListener listener = new KeyListener()
        {
            @Override
            public void keyPressed(KeyEvent event) 
            {}
            @Override
            public void keyReleased(KeyEvent event) 
            { 
                for (Entity eArr[] : entities)
                    for (Entity ePtr : eArr)
                        if (ePtr != null)
                            ePtr.onEvent(event);
                tick(); 
                draw();
            }
            @Override
            public void keyTyped(KeyEvent event) {}
        };
        frame.addKeyListener(listener);
    }
    
    void input()
    {
        frame.requestFocus();
    }
    
    void reset(Vec2 size)
    {
        frame.getContentPane().removeAll();
        frame.repaint();
        this.size = new Vec2(size);
        entities = new Entity[size.y][size.x];
        
        JPanel buttonPanel = new JPanel();
        
        JButton tickButton = new JButton("Tick");
        tickButton.addActionListener((ActionEvent e) ->{  
            tick();
            draw();
        });
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((ActionEvent e) ->{  
            save();
        }); 
        
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener((ActionEvent e) ->{  
            load();
        }); 
        
        buttonPanel.add(tickButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        
        commentBox = new JPanel();   
        JScrollPane bottomArea = new JScrollPane(commentBox);
        bottomArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  
        bottomArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  
        commentBox.setLayout(new BoxLayout(commentBox, BoxLayout.Y_AXIS));
        bottomArea.setPreferredSize(new Dimension(500, 50));
        
        final DefaultListModel<String> l1 = new DefaultListModel<>();
        l1.addElement("None");
        l1.addElement(Antelope.class.getName());  
        l1.addElement(Fox.class .getName());  
        l1.addElement(Human.class .getName());
        l1.addElement(Sheep.class .getName());
        l1.addElement(Turtle.class .getName());
        l1.addElement(Wolf.class .getName());
        
        l1.addElement(Belladona.class .getName());
        l1.addElement(Grass.class .getName());
        l1.addElement(Guarana.class .getName());
        l1.addElement(Hogweed.class .getName());
        l1.addElement(SowThisle.class .getName());
        
        final JList<String> list1 = new JList<>(l1);
        list1.setBounds(100,100, 75,75);
        
        buttonPanel.add(list1);
        
        JPanel drawPanel = new JPanel();
        drawPanel.setLayout(new GridLayout(size.x, size.y));
               
        cells = new JButton[size.y][size.x];
        for(int i = 0; i < size.y; ++i)
            for(int j = 0; j < size.x; ++j)
            {
                drawPanel.add(cells[i][j] = new JButton(""));
                cells[i][j].setPreferredSize(new Dimension(j, i));
                cells[i][j].addActionListener
                (
                    (ActionEvent e) ->
                    {
                        System.out.println();
                        if (list1.getSelectedIndex() == -1 || list1.getSelectedValue().equals("None")) 
                            return;
                        try {
                            Dimension idx = ((JButton)e.getSource()).getPreferredSize();
                            killEntity(new Vec2(idx.width, idx.height), null);
                            spawnEntity(new Vec2(idx.width, idx.height), Class.forName(list1.getSelectedValue()));
                            draw();
                        } catch (ClassNotFoundException ex) {
                            System.out.println(ex);
                        }
                    }
                );
            }
        
        
        frame.setSize(1000, 1000);
        frame.add(drawPanel);
        frame.add(buttonPanel);
        frame.add(bottomArea);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    // returns false on collision (cannot spawn entity at given position), true otherwise
    boolean spawnEntity(Vec2 pos, Class c)
    {
        if (pos.x >= size.x || pos.y >= size.y || pos.x < 0 || pos.y < 0 || isOccupied(pos))
            return false;
        try {
            Entity ent;
            ent = (Entity)c.getDeclaredConstructor(World.class).newInstance(this);
            ent.setPosition(pos);
            entities[pos.y][pos.x] = ent;
            comments.add("Organizm " + c.getName() + " zostal stworzony");
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }
    
    boolean killEntity(Vec2 pos, Class c)
    {
        if (isOccupied(pos))
	{
            if(c != null)
                addComment("Organizm " + entities[pos.y][pos.x].getClass().getName() + " zostaje zabity przez " + c.getName());
            else
                addComment("Organizm " + entities[pos.y][pos.x].getClass().getName() + " zostal zniszczony");
            entities[pos.y][pos.x] = null;
            return true;
	}
	return false;
    }


    void swap(Vec2 pos1, Vec2 pos2)
    {
        if (pos1.y < size.y && pos2.y < size.y && pos1.x < size.x && pos2.x < size.x)
        {
            Vec2 pos1Cp = new Vec2(pos1);
            Vec2 pos2Cp = new Vec2(pos2);
        
            if(isOccupied(pos1Cp))
                entities[pos1Cp.y][pos1Cp.x].setPosition(pos2Cp);
            if(isOccupied(pos2Cp))
                entities[pos2Cp.y][pos2Cp.x].setPosition(pos1Cp);

            Entity temp = getEntityPtr(pos1Cp);
            entities[pos1Cp.y][pos1Cp.x] = getEntityPtr(pos2Cp);
            entities[pos2Cp.y][pos2Cp.x] = temp;
        }
    }


    void tick()
    {
	for (Entity eArr[] : entities)
            for (Entity ePtr : eArr)
                if (ePtr != null)
                    ordEntities.add(ePtr);
        ordEntities.sort((Entity e1, Entity e2)->e1.getStrength()-e2.getStrength());
        
        for(Entity ent : ordEntities)
        {
            Vec2 pos = ent.getPosition();
            if (entities[pos.y][pos.x] == ent) // tick only on alive entities
            {
                ent.tick();
                ent.addAge();
            }
        }
        ordEntities.clear();
    }

    void draw()
    {
        //commentBox.removeAll();
        commentBox.add(new JLabel("\n"), 0);
        for(int i = 0; i < size.y; ++i)
            for(int j = 0; j < size.x; ++j)
            {
                cells[i][j].setBackground(Color.white);
                cells[i][j].setText("");
            }
        
        for (Entity eArr[] : entities)
        {
            for (Entity ePtr : eArr)
                if (ePtr != null)
                    ePtr.draw(cells);
        }
        
        for(String com : comments)
            commentBox.add(new JLabel(com), 0);        
        comments.clear();
        frame.setVisible(true);
    }


    void save()
    {
        try
        {
            FileWriter fileWriter = new FileWriter("save");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("%d %d\n", size.x, size.y);
            for (Entity[] eArr : entities)
                    for (Entity ePtr : eArr)
                            if (ePtr != null)
                            {
                                printWriter.printf("%s %d %d\n", ePtr.getClass().getName(), ePtr.getPosition().x, ePtr.getPosition().y);
                                ePtr.save(printWriter);
                            }
            printWriter.close();
        }
        catch(Exception e)
        {
            System.out.println(e); 
        }
    }

    void load()
    {
        try
        {
            Scanner scanner = new Scanner(new File("save"));
            
            size.x = scanner.nextInt();
            size.y = scanner.nextInt();
            reset(size);
            while(scanner.hasNext())
            {
                Class c = Class.forName(scanner.next());
                
                Vec2 pos = new Vec2(0,0);
                pos.x = scanner.nextInt();
                pos.y = scanner.nextInt();
                spawnEntity(pos, c);
                entities[pos.y][pos.x].load(scanner);
            }
        }
        catch(Exception e)
        {
           System.out.println(e); 
        }
        draw();
    }

    void addComment(String comment)
    {
        comments.add(comment);
    }


    Entity getEntityPtr(Vec2 pos)
    {
        return entities[pos.y][pos.x];
    }

    boolean isOccupied(Vec2 pos)
    {
        return (getEntityPtr(pos) != null);
    }

    Vec2 getSize()
    {
        return new Vec2(size);
    }
    
    private Vec2  size;

    private Entity[][] entities;
    private ArrayList<Entity> ordEntities = new ArrayList<>();
    private ArrayList<String> comments = new ArrayList<>();
    
    private JFrame frame = new JFrame("Paweł Głomski 172026");
    private JButton[][] cells;
    private JPanel commentBox; 
    
}
