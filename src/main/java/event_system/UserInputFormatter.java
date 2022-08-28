package event_system;

import domain_logic.Allergen;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputFormatter {

    public HashSet formatCommaSeperatedAllergens(String commaSeperatedAllergens){
        HashSet<Allergen> allergens = new HashSet<>();

        String[] allergensArray = commaSeperatedAllergens.split(",");
        for (String allergenString : allergensArray) {
            StringBuffer stringBuffer = new StringBuffer();
            Matcher matcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(allergenString);
            while (matcher.find()) {
                matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase() + matcher.group(2).toLowerCase());
            }
            String formattedAllergen = matcher.appendTail(stringBuffer).toString();
            if (Allergen.valueOf(formattedAllergen) != null)
                allergens.add(Allergen.valueOf(formattedAllergen));
        }

        return allergens;
    }

    public BigDecimal formatPrice(String priceString){
        String[] preAndPostCommaDigit = priceString.split(",");
        String formattedPrice="";
        if (preAndPostCommaDigit.length > 1) {
            formattedPrice=preAndPostCommaDigit[0]+".";
            for (int k = 1; k < preAndPostCommaDigit.length; k++) {
                formattedPrice=formattedPrice+preAndPostCommaDigit[k];
            }
        }else {
            formattedPrice=preAndPostCommaDigit[0];
        }

        return new BigDecimal(formattedPrice);
    }

    public int parseStringToInt(String stringValue){
        int intValue=-1;

        try {
            intValue = Integer.parseInt(stringValue);
        } catch (Exception e) {
            System.out.println(e);
        }

        return intValue;
    }
}
