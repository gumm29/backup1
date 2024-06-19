public class Invert{
    public static void main(String[] args) {
        String name = "Gustavo";
        String newName = "";
        for(int i = name.length() -1; i >= 0; i--){
            newName += name.charAt(i);
        }
        System.out.println(newName);
    }
}