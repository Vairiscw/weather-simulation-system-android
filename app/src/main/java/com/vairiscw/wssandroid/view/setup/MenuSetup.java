package com.vairiscw.wssandroid.view.setup;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.vairiscw.wssandroid.API.template.TemplateController;
import com.vairiscw.wssandroid.R;

public class MenuSetup {
    View mainView;
    ImageView template_button;
    TemplateController templateController;

    public MenuSetup(View mainView, TemplateController templateController) {
        this.mainView = mainView;
        this.templateController = templateController;
    }

    public void menuSetup() {
        template_button = mainView.findViewById(R.id.templates_menu_button);
        template_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(mainView.getContext(), v);
        popupMenu.inflate(R.menu.menu_tamplates);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.template_save) {
                    templateController.saveTemplate();
                    return true;
                }
                else if (id == R.id.show_templates) {

                    return true;
                }
                else if (id ==  R.id.template_delete) {

                    return true;
                }
                else
                    return false;

            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }
}
