package com.raven.main;

import com.fpt.utils.Auth;
import com.fpt.utils.MsgBox;
import com.fpt.utils.XImage;
import com.raven.component.Header;
import com.raven.component.Menu;
import com.raven.event.EventMenuSelected;
import com.raven.event.EventShowPopupMenu;
import com.raven.form.FormChangePassword;
import com.raven.form.FormChangeProducts;
import com.raven.form.FormCustomer;
import com.raven.form.FormImportProducts;
import com.raven.form.FormInvoiceChangeProduct;
import com.raven.form.FormInvoiceImportProducts;
import com.raven.form.FormInvoiceSell;
import com.raven.form.FormItems;
import com.raven.form.FormListEmpolyee;
import com.raven.form.FormMyProfile;
import com.raven.form.FormProducts;
import com.raven.form.FormInvoiceReturnProduct;
import com.raven.form.FormReturnProducts;
import com.raven.form.FormRevenueStatistics;
import com.raven.form.FormSalesStatistics;
import com.raven.form.FormSell;
import com.raven.form.FormSupplier;
import com.raven.form.FormVoucher;
import com.raven.form.Form_Home;
import com.raven.form.MainForm;
import com.raven.swing.MenuItem;
import com.raven.swing.PopupMenu;
import com.raven.swing.icon.GoogleMaterialDesignIcons;
import com.raven.swing.icon.IconFontSwing;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Main extends javax.swing.JFrame {

    private MigLayout layout;
    private Menu menu;
    private Header header;
    private MainForm main;
    private Animator animator;

    public Main() {
        initComponents();
        init();
//        setIconImage(XImage.getAppImage());
        Image icon = Toolkit.getDefaultToolkit().getImage("src\\com\\raven\\icon\\shop (6).png");
        this.setIconImage(icon);
    }

    private void init() {
        setLocationRelativeTo(null);    //cho ra giữa màn hình
        setExtendedState(JFrame.MAXIMIZED_BOTH);//cho toàn màn hình 
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        bg.setLayout(layout);
        menu = new Menu();
        header = new Header();
        main = new MainForm();
        menu.addEvent(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
                System.out.println("Menu Index : " + menuIndex + " SubMenu Index " + subMenuIndex);
                if (Auth.isManager()) {
                    if (menuIndex == 0) {
                        main.showForm(new Form_Home());
                    } else if (menuIndex == 1) {
                        if (subMenuIndex == 0) {
                            main.showForm(new FormProducts());
                        } else if (subMenuIndex == 1) {
                            main.showForm(new FormItems());
                        } else if (subMenuIndex == 2) {
                            main.showForm(new FormSupplier());
                        }
                    } else if (menuIndex == 2) {
                        if (subMenuIndex == 0) {
                            main.showForm(new FormImportProducts());
                        } else if (subMenuIndex == 1) {
                            main.showForm(new FormSell());
                        } else if (subMenuIndex == 2) {
                            main.showForm(new FormReturnProducts());
                        } else if (subMenuIndex == 3) {
                            main.showForm(new FormChangeProducts());
                        } else if (subMenuIndex == 4) {
                            main.showForm(new FormInvoiceSell());
                        } else if (subMenuIndex == 5) {
                            main.showForm(new FormInvoiceImportProducts());
                        } else if (subMenuIndex == 6) {
                            main.showForm(new FormInvoiceReturnProduct());
                        } else if (subMenuIndex == 7) {
                            main.showForm(new FormInvoiceChangeProduct());
                        }
                    } else if (menuIndex == 3) {
                        if (subMenuIndex == 0) {
                            main.showForm(new FormRevenueStatistics());
                        } else if (subMenuIndex == 1) {
                            main.showForm(new FormSalesStatistics());
                        }
                    } else if (menuIndex == 4) {
                        if (subMenuIndex == 0) {
                            main.showForm(new FormListEmpolyee());
                        } else if (subMenuIndex == 1) {
//                            main.showForm(new FormSalary());
                        }
                    } else if (menuIndex == 5) {
                        main.showForm(new FormCustomer());
                    } else if (menuIndex == 6) {
                        if (subMenuIndex == 0) {
                            main.showForm(new FormMyProfile());
                        } else if (subMenuIndex == 1) {
                            main.showForm(new FormChangePassword());
                        }
                    } else if (menuIndex == 7) {
                        main.showForm(new FormVoucher());
                    } else {
                        int i = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn đăng xuất không");
                        if (i == JOptionPane.YES_OPTION) {
                            Auth.clear();
                            dispose();
                            new Login_main().setVisible(true);
                        } else {
                            return;
                        }
                    }
                } else {
                    if (menuIndex == 0) {
                        main.showForm(new Form_Home());
                    } else if (menuIndex == 1) {
                        if (subMenuIndex == 0) {
                            main.showForm(new FormSell());
                        } else if (subMenuIndex == 1) {
                            main.showForm(new FormReturnProducts());
                        } else if (subMenuIndex == 2) {
                            main.showForm(new FormChangeProducts());

                        } else if (subMenuIndex == 3) {
                            main.showForm(new FormInvoiceSell());
                        } else if (subMenuIndex == 4) {
                            main.showForm(new FormInvoiceReturnProduct());
                        } else if (subMenuIndex == 5) {
                            main.showForm(new FormInvoiceChangeProduct());
                        }
                    } else if (menuIndex == 2) {
                        if (subMenuIndex == 0) {
                            main.showForm(new FormRevenueStatistics());
                        } else if (subMenuIndex == 1) {
                            main.showForm(new FormSalesStatistics());
                        }
                    } else if (menuIndex == 3) {
                        main.showForm(new FormCustomer());
                    } else if (menuIndex == 4) {
                        if (subMenuIndex == 0) {
                            main.showForm(new FormMyProfile());
                        } else if (subMenuIndex == 1) {
                            main.showForm(new FormChangePassword());
                        }
                    } else {
                        int i = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn đăng xuất không");
                        if (i == JOptionPane.YES_OPTION) {
                            Auth.clear();
                            dispose();
                            new Login_main().setVisible(true);
                        } else {
                            return;
                        }

                    }
                }

            }
        });
        menu.addEventShowPopup(new EventShowPopupMenu() {
            @Override
            public void showPopup(Component com) {
                MenuItem item = (MenuItem) com;
                PopupMenu popup = new PopupMenu(Main.this, item.getIndex(), item.getEventSelected(), item.getMenu().getSubMenu());
                int x = Main.this.getX() + 52;
                int y = Main.this.getY() + com.getY() + 86;
                popup.setLocation(x, y);
                popup.setVisible(true);
            }
        });
        menu.initMenuItem();
        bg.add(menu, "w 230!, spany 2");    // Span Y 2cell
        bg.add(header, "h 50!, wrap");
        bg.add(main, "w 100%, h 100%");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 60 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!, spany2");
                menu.revalidate();
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
            }

        };
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        header.addMenuEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    animator.start();
                }
                menu.setEnableMenu(false);
                if (menu.isShowMenu()) {
                    menu.hideallMenu();
                }
            }
        });
        //  Init google icon font
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        //  Start with this form
        main.showForm(new Form_Home());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IT Shop APP");

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1366, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 783, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
