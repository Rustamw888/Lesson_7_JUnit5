package guru.qa.homework.enums;

public enum Sections {

    F("Фильмы"), S("Сериалы"), M("Мультфильмы");

    public final String sectionName;

    Sections(String sectionName) {
        this.sectionName = sectionName;
    }
}
