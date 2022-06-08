import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

import Network.HTTPRequest;

public class App {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_RED = "\u001B[31m";

        public static void main(String[] args) throws Exception {
                Scanner read = new Scanner(System.in);

                int decision;
                boolean repeting = true;

                System.out.println("Enter your name: " + ANSI_CYAN);
                String Trainer = read.next();
                System.out.println(ANSI_RESET + "Welcome trainer " + Trainer + "!");

                JSONObject jsonObject = new HTTPRequest()
                                .requestGetMethod("https://pokeapi.co/api/v2/pokemon/?offset=151&limit=151");

                JSONArray array = jsonObject.getJSONArray("results");

                while (repeting == true) {
                        System.out.print(ANSI_RESET + "\n-----------------------");
                        System.out.print("\n- AVAIBLE POKEMONS -\n");
                        System.out.print("-----------------------\n\n");
                        for (int index = 0; index < 151; index++) {

                                JSONObject pokeObject = array.getJSONObject(index);

                                String pokeNome = pokeObject.getString("name");

                                System.out.println("[" + (index + 1) + "] - " + pokeNome);
                        }
                        System.out.println(
                                        "\n\nEnter the digit of the Pokemon chosen to get it's details: " + ANSI_CYAN);
                        int pokeIndex = read.nextInt();

                        while (false == isBetween(pokeIndex, 1, 152)) {
                                System.out.println(ANSI_RESET +
                                                "\n Invalid value! Enter the digit of the Pokemon chosen to get it's details: "
                                                + ANSI_CYAN);
                                pokeIndex = read.nextInt();
                        }

                        JSONObject pokeObject = array.getJSONObject((pokeIndex - 1));
                        String pokeNome = pokeObject.getString("name");
                        String pokeUrl = pokeObject.getString("url");

                        JSONObject JsonObject = new HTTPRequest()
                                        .requestGetMethod(pokeUrl);

                        System.out.println(ANSI_RED + "\n | [" + pokeIndex + "]" + " -> " + pokeNome);

                        // IMAGE
                        Img2Ascii img = new Img2Ascii();
                        img.convertToAscii(JsonObject.getJSONObject("sprites").getString("front_default"));

                        // STATS
                        System.out.println("\n | Stats: ");
                        JSONArray arrayStats = JsonObject.getJSONArray("stats");
                        for (int count = 0; count < arrayStats.length(); count++) {
                                JSONObject ObjectStatIndex = arrayStats.getJSONObject(count);
                                JSONObject ObjectStat = ObjectStatIndex.getJSONObject("stat");
                                String pokeStat = ObjectStat.getString("name");
                                String pokeStatUrl = ObjectStat.getString("url");
                                JSONObject JsonObjectStats = new HTTPRequest()
                                                .requestGetMethod(pokeStatUrl);
                                JSONArray arrayStatCharac = JsonObjectStats.getJSONArray("characteristics");
                                JSONObject ObjectStatCharac = arrayStatCharac.getJSONObject(1);
                                pokeStatUrl = ObjectStatCharac.getString("url");
                                JSONObject JsonObjectStatsCharac = new HTTPRequest()
                                                .requestGetMethod(pokeStatUrl);
                                JSONArray arrayStatCharacDesc = JsonObjectStatsCharac.getJSONArray("descriptions");
                                JSONObject ObjectStatCharacdesc = arrayStatCharacDesc.getJSONObject(7);
                                String pokeStatDesc = ObjectStatCharacdesc.getString("description");
                                System.out.println(" | " + pokeStat + " -> " + pokeStatDesc);

                        }
                        // TYPES
                        JSONArray arrayTypes = JsonObject.getJSONArray("types");
                        JSONObject ObjectTypes0 = arrayTypes.getJSONObject(0);
                        JSONObject ObjectTypes = ObjectTypes0.getJSONObject("type");
                        String pokeType = ObjectTypes.getString("name");

                        System.out.println("\n | Types -> " + pokeType);

                        // ABILITIES
                        System.out.println("\n | Abilities: ");
                        JSONArray arrayAbilities = JsonObject.getJSONArray("abilities");
                        for (int count = 0; count < arrayAbilities.length(); count++) {

                                JSONObject ObjectAbilities = arrayAbilities.getJSONObject(count);
                                JSONObject ObjectAbility = ObjectAbilities.getJSONObject("ability");
                                String pokeAbility = ObjectAbility.getString("name");
                                String pokeAbilityUrl = ObjectAbility.getString("url");

                                JSONObject JsonObjectAbb = new HTTPRequest()
                                                .requestGetMethod(pokeAbilityUrl);
                                JSONArray arrayAbbdesc = JsonObjectAbb.getJSONArray("effect_entries");
                                JSONObject ObjectAbbdesc = arrayAbbdesc.getJSONObject(1);

                                JSONObject ObjectAbbLanguage = ObjectAbbdesc.getJSONObject("language");
                                String AbbLanguage = ObjectAbbLanguage.getString("name");

                                if (AbbLanguage == "en" && count == 0) {
                                        ObjectAbbdesc = arrayAbbdesc.getJSONObject(1);
                                        AbbLanguage = ObjectAbbLanguage.getString("name");
                                } else if (AbbLanguage == "en" && count == 1) {
                                        ObjectAbbdesc = arrayAbbdesc.getJSONObject(0);
                                        AbbLanguage = ObjectAbbLanguage.getString("name");
                                }

                                String pokeAbbdesc = ObjectAbbdesc.getString("effect");
                                System.out.println(" | " + pokeAbility);
                                System.out.println(pokeAbbdesc);
                        }

                        // MOVES
                        int count2 = 4;
                        System.out.print("\n\n | Moves: \n |  ");
                        JSONArray arrayMoves = JsonObject.getJSONArray("moves");
                        for (int count = 0; count < arrayMoves.length(); count++) {
                                JSONObject ObjectMoves = arrayMoves.getJSONObject(count);
                                JSONObject ObjectMove = ObjectMoves.getJSONObject("move");
                                String pokeMove = ObjectMove.getString("name");

                                System.out.print(pokeMove + "   ");
                                if (count == count2) {
                                        System.out.print("\n |  ");
                                        count2 = count2 + 4;
                                }

                        }

                        System.out.println(ANSI_RESET +
                                        "\n\nPress a number to get back Avaible Pokemons, else, press 0 to exit: "
                                        + ANSI_CYAN);
                        decision = read.nextInt();
                        if (decision != 0) {
                                repeting = true;
                        } else {
                                repeting = false;
                        }
                }
                System.out.println(ANSI_RESET + "\n See you " + Trainer + "!");
                read.close();

        }

        public static boolean isBetween(int x, int lower, int upper) {
                return lower <= x && x <= upper;
        }
}
