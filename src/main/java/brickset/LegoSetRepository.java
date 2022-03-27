package brickset;

import repository.Repository;

import java.util.Comparator;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }


    /** Returns the number of Lego sets with the specified tag
     *
     * @param tag a Lego set tag
     * @return the number of Lego sets with the specified tag
     */
    public long countLegoSetsWithTag(String tag) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null && legoSet.getTags().contains(tag))
                .count();
    }

    /** Prints out the Lego sets with the specified packaging type sorted alphabetically
     *
     * @param type a Lego set packaging type (if not given, type is "NOT_SPECIFIED")
     */
    public void printLegoSetsWithPackagingType(String type){
        String typeFinal = (type.length() == 0) ? "NOT_SPECIFIED" : type;
        getAll().stream()
                .filter(legoSet -> legoSet.getPackagingType().toString().toLowerCase().equals(typeFinal.toLowerCase()))
                .map(LegoSet::getName)
                .sorted()
                .forEach(System.out::println);
    }

    /** Prints out Lego sets between the two years specified
     *
     * @param year1 the lower limit of the year
     * @param year2 the upper limit of the year
     */
    public void printLegoSetsBetweenYears(int year1,int year2){
        if(year1 > year2){ int temp = year1; year1 = year2; year2 = temp;}
        int year1Final = year1;
        int year2Final = year2;
        getAll().stream()
                .filter(legoSet -> legoSet.getYear().getValue() >= year1Final && legoSet.getYear().getValue() <= year2Final)
                .forEach(System.out::println);
    }

    /** Counts Lego sets with dimension specified
     *
     * @return the number of the Lego sets with dimensions specified
     */
    public long countLegoSetsWithDimensions(){
        return getAll().stream()
                .filter(legoSet -> legoSet.getDimensions() != null)
                .count();
    }

    /** Prints out the name of the Lego sets with a weight not heavier than the parameter
     *
     * @param weight the max weight of the Lego set
     */
    public void printLegoSetsWithMaxWeight(double weight){
        getAll().stream()
                .filter(legoSet -> legoSet.getDimensions() != null && legoSet.getDimensions().getWeight() != null && legoSet.getDimensions().getWeight() <= weight)
                .map(LegoSet::getName)
                .forEach(System.out::println);
    }

    /** Returns the name of the Lego set with the most pieces
     *
     * @return the name of the lego set with the most pieces
     */
    public String legoSetWithMostPcs(){
        return getAll().stream().max(Comparator.comparingInt(LegoSet::getPieces)).map(LegoSet::getName).get();
    }




    public static void main(String[] args) {
        var repository = new LegoSetRepository();
        System.out.println("Counting Lego sets with tag \"Microscale\":");
        System.out.println(repository.countLegoSetsWithTag("Microscale"));

        System.out.println("Printing Lego sets with packaging type \"box\":");
        repository.printLegoSetsWithPackagingType("Box");

        System.out.println("Printing Lego sets released in 2009");
        repository.printLegoSetsBetweenYears(2009,2009);

        System.out.println("Counting Lego sets that have dimensions specified");
        System.out.println(repository.countLegoSetsWithDimensions());

        System.out.println("Printing lego sets not heavier than 0.1");
        repository.printLegoSetsWithMaxWeight(0.1);

        System.out.println("Name of the lego set with the most pieces: "+repository.legoSetWithMostPcs());
    }



}
