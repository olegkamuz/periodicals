package ua.kharkov.knure.dkolesnikov.st4example.db.entity;

/**
 * Theme entity.
 * 
 */
public class Theme extends Entity {

	private static final long serialVersionUID = 2386302708905518585L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Theme [name=" + name + ", getId()=" + getId() + "]";
	}

}
