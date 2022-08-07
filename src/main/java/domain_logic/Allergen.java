package domain_logic;

import java.util.HashSet;

public enum Allergen {
    Gluten,Erdnuss,Haselnuss,Sesamsamen;

    public HashSet<Allergen> getAllergene(String[] allergene){
        HashSet<Allergen> a=new HashSet<>();

        for (String i : allergene){
            switch (i){
                case "Gluten":
                    a.add(Gluten);
                    break;
                case "Erdnuss":
                    a.add(Erdnuss);
                    break;
                case "Haseluss":
                    a.add(Haselnuss);
                    break;
                case "Sesamsamen":
                    a.add(Sesamsamen);
                    break;
                default:
                    break;
            }
        }
        return a;
    }
}
