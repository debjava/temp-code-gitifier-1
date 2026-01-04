package com.ddlab.rnd.ui.util;

import com.ddlab.rnd.constants.CommonConstants;
import com.ddlab.rnd.setting.PublisherSetting;
import com.ddlab.rnd.ui.GitPanelComponent;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class UIUtil {

    public static void populateOneRow(DefaultTableModel tableModel) {
        Object[] newRow = new Object[]{CommonConstants.GIT_CHOICES[0], "", "", "Remove"};
        tableModel.addRow(newRow);
    }

    public static void populateTableData(DefaultTableModel tableModel) {
        PublisherSetting setting = PublisherSetting.getInstance();
        Map<String, String> tableMap = setting.getGitInfoTableMap();
        log.debug("Table Map from Setting --->{}", tableMap);
        log.debug("Table Model Data Count: {}", tableMap.size());

        if (tableMap == null || tableMap.isEmpty()) {
            populateOneRow(tableModel);
        } else {
            populateTableModel(tableMap, tableModel);
        }
    }

    public static void populateTableModel(Map<String, String> tableMap, DefaultTableModel tableModel) {
        tableMap.forEach((key, value) -> {
            String gitToken = value;
            String gitHostName = CommonConstants.GIT_CHOICES[0];
            String gitUsername = "";
            String[] keyValues = key.split("~");
            if (keyValues.length == 2) {
                gitHostName = keyValues[0];
                gitUsername = keyValues[1];
            }
            Object[] newRow = new Object[]{gitHostName, gitUsername, gitToken, "Remove"};
            tableModel.addRow(newRow);

        });
    }

    public static void saveSetting(GitPanelComponent gitPanelComponent) {
        PublisherSetting setting = PublisherSetting.getInstance();
        JTable gitInfoTable = gitPanelComponent.getGitInfoTable();

        if (gitInfoTable.isEditing()) {
            gitInfoTable.getCellEditor().stopCellEditing();
        }

        LinkedHashMap<String, String> gitInfoTableMap = new LinkedHashMap<String, String>();
        DefaultTableModel model = (DefaultTableModel) gitInfoTable.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            String selectionGitItem = String.valueOf(model.getValueAt(row, 0));
            String userNameField = String.valueOf(model.getValueAt(row, 1));
            String tokenField = String.valueOf(model.getValueAt(row, 2));

            gitInfoTableMap.put(selectionGitItem + "~" + userNameField, tokenField);
        }
        setting.setGitInfoTableMap(gitInfoTableMap);
    }

    public static void resetReloadSetting(GitPanelComponent gitPanelComponent) {
        JTable gitInfoTable = gitPanelComponent.getGitInfoTable();
        if (gitInfoTable.isEditing()) {
            gitInfoTable.getCellEditor().stopCellEditing();
        }
        DefaultTableModel model = (DefaultTableModel) gitInfoTable.getModel();
        populateTableData(model);
    }
}
