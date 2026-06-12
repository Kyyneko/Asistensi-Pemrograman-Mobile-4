package com.example.skillsaga.model;

public class CategoryModel {
    private String name;
    private int iconResId;
    private String colorHexBg;
    private String colorHexIcon;

    public CategoryModel(String name, int iconResId, String colorHexBg, String colorHexIcon) {
        this.name = name;
        this.iconResId = iconResId;
        this.colorHexBg = colorHexBg;
        this.colorHexIcon = colorHexIcon;
    }

    public String getName() { return name; }
    public int getIconResId() { return iconResId; }
    public String getColorHexBg() { return colorHexBg; }
    public String getColorHexIcon() { return colorHexIcon; }
}