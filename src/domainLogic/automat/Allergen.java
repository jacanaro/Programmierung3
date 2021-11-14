package domainLogic.automat;

import java.util.HashSet;

public enum Allergen {
    Gluten,Erdnuss,Haselnuss,Sesamsamen;

    public HashSet<Allergen> getAllergene(String[] allergene){
        HashSet<Allergen> a=new HashSet<>();
        //format input -> use for cli
        /*
        for (String i : allergene) {
            StringBuffer sb = new StringBuffer();
            Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(i);
            while (m.find()) {
                m.appendReplacement(sb, m.group(1).toUpperCase() + m.group(2).toLowerCase());
            }
            String formattedAllergen = m.appendTail(sb).toString();

            if (Allergen.valueOf(formattedAllergen) != null) this.allergene.add(Allergen.valueOf(formattedAllergen));
        }
         */
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
