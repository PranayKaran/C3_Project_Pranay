import org.junit.Before;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    List<Item> restaurantMenu = new ArrayList<Item>();

    @BeforeEach
    public void setup(){
        Item item1 = new Item("Biryani", 100);
        Item item2 = new Item("Kofta", 100);
        Item item3 = new Item("Tikka", 100);
        Item item4 = new Item("Halwa", 100);
        restaurantMenu.add(item1);
        restaurantMenu.add(item2);
        restaurantMenu.add(item3);
        restaurantMenu.add(item4);

    }

    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Paradise","Hyderabad",openingTime,closingTime);
        Restaurant result = service.findRestaurantByName(restaurant.getName());
        assertEquals(result.getName(),restaurant.getName());
        assertEquals(result.openingTime,restaurant.openingTime);
        assertEquals(result.closingTime,restaurant.closingTime);
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {

        restaurantNotFoundException exception = assertThrows(restaurantNotFoundException.class, () -> {
            service.findRestaurantByName("not-found");
        });
        assertEquals(exception.getMessage(),"not-found");
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void selected_items_present_in_the_menu_recived_order_total() throws itemNotFoundException {
        List<String> itemNames = new ArrayList<>();
        itemNames.add("Biryani");
        itemNames.add("Halwa");
        itemNames.add("Tikka");
        assertEquals(300,service.orderTotal(itemNames,restaurantMenu));

    }

    @Test
    public void selected_items_not_present_in_the_menu_should_throw_exception() throws itemNotFoundException {
        List<String> itemNames = new ArrayList<>();
        itemNames.add("Biryani");
        itemNames.add("Halwa");
        itemNames.add("Puri");
        itemNotFoundException exception = assertThrows(itemNotFoundException.class, ()-> {service.orderTotal(itemNames,restaurantMenu);});

        assertEquals("Puri",exception.getMessage());
    }
}
