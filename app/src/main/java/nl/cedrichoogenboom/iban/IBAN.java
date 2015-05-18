package nl.cedrichoogenboom.iban;

import java.math.BigInteger;

/**
 * Created by ced on 16-5-15.
 */
public class IBAN {

    private String[][] banken = {
            {"00", "INGB"},
            {"10", "RABO"},
            {"11", "RABO"},
            {"12", "RABO"},
            {"13", "RABO"},
            {"14", "RABO"},
            {"15", "RABO"},
            {"16", "RABO"},
            {"17", "RABO"},
            {"18", "RABO"},
            {"19", "TRIO"},
            {"21", "ABNA"},
            {"22", "ABNA"},
            {"23", "ABNA"},
            {"24", "ABNA"},
            {"25", "ABNA"},
            {"26", "FVLB"},
            {"28", "BNGH"},
            {"29", "FRBK"},
            {"30", "RABO"},
            {"31", "RABO"},
            {"32", "RABO"},
            {"33", "RABO"},
            {"34", "RABO"},
            {"35", "RABO"},
            {"36", "RABO"},
            {"37", "RABO"},
            {"38", "RABO"},
            {"39", "TRIO"},
            {"40", "ABNA"},
            {"41", "ABNA"},
            {"42", "ABNA"},
            {"43", "ABNA"},
            {"44", "ABNA"},
            {"45", "ABNA"},
            {"46", "ABNA"},
            {"47", "ABNA"},
            {"48", "ABNA"},
            {"49", "ABNA"},
            {"50", "ABNA"},
            {"51", "ABNA"},
            {"52", "ABNA"},
            {"53", "ABNA"},
            {"54", "ABNA"},
            {"55", "ABNA"},
            {"56", "ABNA"},
            {"57", "ABNA"},
            {"58", "ABNA"},
            {"59", "ABNA"},
            {"61", "ABNA"},
            {"62", "ABNA"},
            {"64", "ABNA"},
            {"65", "INGB"},
            {"66", "INGB"},
            {"67", "INGB"},
            {"68", "INGB"},
            {"69", "INGB"},
            {"71", "ABNA"},
            {"72", "AEGO"},
            {"73", "AEGO"},
            {"74", "AEGO"},
            {"75", "INGB"},
            {"76", "ABNA"},
            {"77", "AEGO"},
            {"79", "INGB"},
            {"80", "ABNA"},
            {"82", "SNSB"},
            {"83", "ABNA"},
            {"84", "ABNA"},
            {"85", "ASNB"},
            {"86", "ABNA"},
            {"87", "ABNA"},
            {"88", "ABNA"},
            {"89", "ABNA"},
            {"90", "SNSB"},
            {"91", "SNSB"},
            {"92", "SNSB"},
            {"93", "SNSB"},
            {"94", "ABNA"},
            {"95", "SNSB"},
            {"96", "SNSB"},
            {"98", "ABNA"}
    };

    private boolean isBankNummer(String rekening) {
        int i;
        int iBank;
        char zero = '0';

        if (rekening.length() < 9) {
            return true;
        }
        else if (rekening.length() > 10)
            return false;
        else {
            iBank = 0;
            for (i = 0; i <= 8; i++) {
                iBank = iBank + (9 - i) * ((int)(rekening.charAt(i)) - ((int)zero));
            }
        }

        return (iBank % 11 == 0);
    }

    private String getBank(String rekening) {
        String value = "";
        String bankCode = "";
        boolean found = false;

        bankCode = rekening.substring(1, 3);

        for (int i = 0; i < banken.length; i++) {
            if (banken[i][0] == bankCode) {
                value = banken[i][1];
                found = true;
                break;
            }
        }

        if (found)
            return value;
        else
            return "Error";
    }

    public String getIBAN(String rekening) {
        String Bank = "";
        String Land = "";
        Integer i;
        BigInteger e;
        String controleGetal = "";
        String bankGetal = "";
        String landGetal = "";
        String iBAN = "";
        Land = "NL";

        if (isBankNummer(rekening)) {
            while (rekening.length() < 10) {
                rekening = "0" + rekening;
            }

            Bank = getBank(rekening);
            if (!Bank.equals("")) {
                for (int j = 0; j < 4; j++)
                    bankGetal = bankGetal + (int)(Bank.toUpperCase().charAt(j) - 55);
                for (int j = 0; j < 2; j++)
                    landGetal = landGetal + (int)(Land.toUpperCase().charAt(j) - 55);
                String CDec = bankGetal + rekening + landGetal + "00";
                e = new BigInteger(CDec);
                i = 98 - (e.mod(new BigInteger("97")).intValue());

                if (i >= 10)
                    controleGetal = i.toString();
                else
                    controleGetal = "0" + i.toString();
            }
            iBAN = Land + controleGetal + Bank + rekening;
        }

    return iBAN;
    }
}
