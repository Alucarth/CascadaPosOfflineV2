/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiwire.print;

import de.enough.polish.ui.Form;
import de.enough.polish.ui.Image;
import de.enough.polish.ui.StringItem;
import java.util.Vector;

/**
 * Template for the print layout.
 *
 * @author sgherghesanu
 */
public abstract class PrintTemplate {

    /**
     * Icon.
     */
    private Image icon;
    /**
     * Title.
     */
    private String title;
    /**
     * Header Image.
     */
    private Image headerImage;
    /**
     * Header text.
     */
    private String headerText;
    /**
     * Body is a form.
     */
    private Form body;
    /**
     * Footer image.
     */
    private Image footerImage;
    /**
     * Footer text.
     */
    private String footerText;

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Make the StringItemsHelper vector.
     * @return Vector
     */
    public abstract Vector makeTree();

    /**
     * array of StringItems from form.
     *
     * @param f form
     * @return array of StringItems from the form
     */
    public abstract StringItem[] getFormStringItems(Form f);

    public Form getBody() {
        return body;
    }

    public void setBody(Form body) {
        this.body = body;
    }

    public Image getFooterImage() {
        return footerImage;
    }

    public void setFooterImage(Image footerImage) {
        this.footerImage = footerImage;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public Image getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(Image headerImage) {
        this.headerImage = headerImage;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }
}
