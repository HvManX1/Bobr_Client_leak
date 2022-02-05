/*
 * Decompiled with CFR 0.150.
 */
package de.vinii.management.ui.clickgui.listeners;

import de.vinii.management.ui.clickgui.components.GuiButton;
import de.vinii.management.ui.clickgui.components.GuiComboBox;
import de.vinii.management.ui.clickgui.components.GuiGetKey;
import de.vinii.management.ui.clickgui.components.GuiLabel;
import de.vinii.management.ui.clickgui.components.GuiSlider;
import de.vinii.management.ui.clickgui.components.GuiToggleButton;
import de.vinii.management.ui.clickgui.components.listeners.ComboListener;
import de.vinii.management.ui.clickgui.components.listeners.ComponentListener;
import de.vinii.management.ui.clickgui.components.listeners.KeyListener;
import de.vinii.management.ui.clickgui.components.listeners.ValueListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ru.internali.CatClient;
import ru.internali.module.Module;
import ru.internali.settings.Setting;

public class ComponentsListener
extends ComponentListener {
    private GuiButton button;

    public ComponentsListener(GuiButton button) {
        this.button = button;
    }

    @Override
    public void addComponents() {
        this.add(new GuiLabel("Settings >"));
        final Module m = CatClient.instance.moduleManager.getModule(this.button.getText());
        if (CatClient.instance.settingsManager.getSettingsByMod(m) != null) {
            for (final Setting set : CatClient.instance.settingsManager.getSettingsByMod(m)) {
                if (set.isSlider()) {
                    GuiSlider slider = new GuiSlider(set.getName(), (float)set.getMin(), (float)set.getMax(), (float)set.getValDouble(), set.onlyInt() ? 0 : 2);
                    slider.addValueListener(new ValueListener(){

                        @Override
                        public void valueUpdated(float value) {
                            set.setValDouble(value);
                        }

                        @Override
                        public void valueChanged(float value) {
                            set.setValDouble(value);
                            new Thread(() -> {}).start();
                        }
                    });
                    this.add(slider);
                }
                if (set.isCheck()) {
                    final GuiToggleButton toggleButton = new GuiToggleButton(set.getName());
                    toggleButton.setToggled(set.getValBoolean());
                    toggleButton.addClickListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            set.setValBoolean(toggleButton.isToggled());
                        }
                    });
                    this.add(toggleButton);
                }
                if (!set.isCombo()) continue;
                GuiComboBox comboBox = new GuiComboBox(set);
                comboBox.addComboListener(new ComboListener(){

                    @Override
                    public void comboChanged(String combo) {
                    }
                });
                this.add(comboBox);
            }
        }
        GuiGetKey ggk = new GuiGetKey("KeyBind", m.getKey());
        ggk.addKeyListener(new KeyListener(){

            @Override
            public void keyChanged(int key) {
                m.setKey(key);
                new Thread(() -> {}).start();
            }
        });
        this.add(ggk);
        final GuiToggleButton toggleButton = new GuiToggleButton("Visable");
        toggleButton.setToggled(m.visible);
        toggleButton.addClickListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                m.visible = toggleButton.isToggled();
            }
        });
        this.add(toggleButton);
    }
}

