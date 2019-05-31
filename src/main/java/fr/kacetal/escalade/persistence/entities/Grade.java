package fr.kacetal.escalade.persistence.entities;

public enum Grade {
    _1("1"),
    _2("2"),
    _3("3"),
    _4a("4a"),
    _4b("4b"),
    _4c("4c"),
    _5a("5a"),
    _5b("5b"),
    _5c("5c"),
    _6a("6a"),
    _6aPLUS("6a+"),
    _6b("6b"),
    _6bPLUS("6b+"),
    _6c("6c"),
    _6cPLUS("6c+"),
    _7a("7a"),
    _7aPLUS("7a+"),
    _7b("7b"),
    _7bPLUS("7b+"),
    _7c("7c"),
    _7cPLUS("7c+"),
    _8a("8a"),
    _8aPLUS("8a+"),
    _8b("8b"),
    _8bPLUS("8b+"),
    _8c("8c"),
    _8cPLUS("8c+"),
    _9a("9a"),
    _9aPLUS("9a+"),
    _9b("9b"),
    _9bPLUS("9b+"),
    _9c("9c");
    
    private String gradeName;
    
    Grade(String name) {
        this.gradeName = name;
    }
    
    @Override
    public String toString() {
        return gradeName;
    }
}
