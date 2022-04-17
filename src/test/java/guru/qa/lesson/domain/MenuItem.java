package guru.qa.lesson.domain;

public enum MenuItem {

    IMG("Картинки"), VID("Видео");

    public final String rusName;

    MenuItem(String rusName) {
        this.rusName = rusName;
    }
}
