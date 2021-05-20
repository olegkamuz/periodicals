package ua.kharkov.knure.dkolesnikov.st4example.db.entity;

/**
 * Magazine item entity.
 */
public class Magazine extends Entity {

    private static final long serialVersionUID = 4716395168539434663L;

    private String name;

    private Integer price;

    private String image;

    private Long themeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "MenuItem [name=" + name + ", price=" + price + ", image=" + image + ", categoryId="
                + themeId + ", getId()=" + getId() + "]";
    }

}