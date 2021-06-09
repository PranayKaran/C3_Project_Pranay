import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException {

        for (Restaurant restaurant: restaurants) {
            if (restaurantName.equals(restaurant.getName())) {
                return restaurant;
            }
        }
        throw new restaurantNotFoundException(restaurantName);
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public int orderTotal(List<String> itemNames, List<Item> restaurantMenu) throws itemNotFoundException{
        int totalPrice = 0;
        for (String itemName: itemNames) {

            Item value = restaurantMenu.stream().filter(item ->
                    item.getName().equals(itemName)).findFirst().orElse(null);
            if (value == null){
                throw new itemNotFoundException(itemName);
            }
            totalPrice = totalPrice +value.getPrice();
        }
        return totalPrice;
    }
}
