/*
 * Decompiled with CFR 0.150.
 */
package de.vinii.management.ui.clickgui.listeners;

import de.vinii.management.ui.clickgui.components.GuiButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ru.internali.CatClient;
import ru.internali.module.Module;

public class ClickListener
implements ActionListener {
    private GuiButton button;

    public ClickListener(GuiButton button) {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Module m = CatClient.instance.moduleManager.getModule(this.button.getText());
        m.toggle();
    }
}

