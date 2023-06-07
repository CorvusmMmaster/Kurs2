import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Road extends JPanel implements ActionListener,Runnable
{
    Timer mainTimer = new Timer(25,this);
    Image img = new ImageIcon("res/bg_road.jpg").getImage();
    Player p=new Player();

    Thread enemiesFactory = new Thread(this);
    java.util.List<Enemy> enemies= new ArrayList<Enemy>();
    public Road()
    {
        mainTimer.start();
        enemiesFactory.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }


    private class MyKeyAdapter extends KeyAdapter
    {
        public void keyPressed (KeyEvent e)
        {
            p.keyPressed(e);
        }
        public void keyReleased(KeyEvent e)
        {
            p.keyReleased(e);
        }
    }

public  void paint(Graphics g)
    {
        g=(Graphics2D) g;
        g.drawImage(img,p.layer1,0,null);
        g.drawImage(img,p.layer2,0,null);
        g.drawImage(p.img,p.x,p.y,null);

        float v=(200/Player.MAX_V) * p.v;
        g.setColor(Color.GREEN);
        Font font = new Font("Times new Roman",Font.ITALIC, 23);
        g.setFont(font);
        g.drawString("Speed: "+v+" km/h", 600, 680);


        Iterator<Enemy> i= enemies.iterator();
        while(i.hasNext())
        {
            Enemy e=i.next();
            if(e.x>= 4000 || e.x <=-2000)
            {
                i.remove();
            }
            else{
                e.move();
                g.drawImage(e.img, e.x, e.y, null);

            }
        }
    }
    public void  actionPerformed(ActionEvent e)
    {
        p.move();
        repaint();
        testCollisionWithEnemies();
    }
    private void  testCollisionWithEnemies()
    {
        Iterator<Enemy> i= enemies.iterator();
        while(i.hasNext())
        {
            Enemy e = i.next();
            if(p.getRect().intersects(e.getRect()))
            {
                JOptionPane.showMessageDialog(null,"Жига на металл.");
                System.exit(1);
            }
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            Random rand=new Random();
            try {
                Thread.sleep(rand.nextInt(2000));
                enemies.add(new Enemy(1350,
                        rand.nextInt(600),
                        rand.nextInt(100),
                        this));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}