package com.github.yyy.www.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StartupUtil {

    public static List<JSONObject> convertTree(JSONObject jsonRoot) {
        // 获取原始 JSON 数据
        JSONArray events = jsonRoot.getJSONObject("timeline").getJSONArray("events");

        // 用 Map 存储每个节点的 ID 和对应的 JSONObject
        Map<Integer, JSONObject> nodeMap = new HashMap<>();
        for (int i = 0; i < events.size(); i++) {
            JSONObject jsonObj = events.getJSONObject(i);
            int id = jsonObj.getJSONObject("startupStep").getIntValue("id");
            nodeMap.put(id, jsonObj);
        }

        // 构建树形结构
        List<JSONObject> roots = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            JSONObject jsonObj = events.getJSONObject(i);
            int parentId = jsonObj.getJSONObject("startupStep").getIntValue("parentId");
            if (parentId == 0) {
                // 根节点
                roots.add(jsonObj);
            } else {
                // 非根节点
                JSONObject parentNode = nodeMap.get(parentId);
                JSONArray children = parentNode.getJSONArray("children");
                if (children == null) {
                    children = new JSONArray();
                    parentNode.put("children", children);
                }
                children.add(jsonObj);
            }
        }

        return roots;
    }

    public static DefaultMutableTreeNode toSwing(String jsonStr) {

        // 转为树形结构
        JSONObject jsonRoot = JSON.parseObject(jsonStr);
        List<JSONObject> roots = convertTree(jsonRoot);

        // 将根节点转换为 DefaultMutableTreeNode 对象
        List<DefaultMutableTreeNode> rootNodes = new ArrayList<>();
        for (JSONObject rootNode : roots) {
            DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(generalName(rootNode));
            createTreeNodes(treeNode, rootNode.getJSONArray("children"));
            rootNodes.add(treeNode);
        }

        // 定义 JTree
        DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
        rootTreeNode.setUserObject("ROOT\n"
                + "springBootVersion：" + jsonRoot.getString("springBootVersion") + "\n"
                + "startTime：" + jsonRoot.getJSONObject("timeline").getString("startTime")
        );
        for (DefaultMutableTreeNode rootNode : rootNodes) {
            rootTreeNode.add(rootNode);
        }

        return rootTreeNode;

    }

    // 递归创建树节点
    private static void createTreeNodes(DefaultMutableTreeNode parent, JSONArray children) {
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                JSONObject childNode = children.getJSONObject(i);
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(generalName(childNode));
                parent.add(child);
                createTreeNodes(child, childNode.getJSONArray("children"));
            }
        }
    }

    // 生成节点名称
    private static String generalName(JSONObject node) {
        // name
        String name = node.getJSONObject("startupStep").getString("name");
        // tag
        JSONArray tags = node.getJSONObject("startupStep").getJSONArray("tags");
        String tagsStr = tags.stream().map(o -> ((JSONObject) o).getString("key") + "：" + ((JSONObject) o).getString("value")).collect(Collectors.joining("\n"));
        // duration
        String duration = node.getString("duration");
        return "类型：" + name + "\n" + (StrUtil.isNotBlank(tagsStr) ? tagsStr + "\n" : "") + " 耗时：" + duration;
    }

}
