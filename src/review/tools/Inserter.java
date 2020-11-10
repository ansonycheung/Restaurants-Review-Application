package review.tools;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import review.dal.*;
import review.model.*;

/**
 * main() runner, used for the app demo.
 * 
 * Instructions: 1. Create a new MySQL schema and then run the CREATE TABLE
 * statements from lecture: http://goo.gl/86a11H. 2. Update ConnectionManager
 * with the correct user, password, and schema.
 */
public class Inserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances.
		UsersDao usersDao = UsersDao.getInstance();
		CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
		CompaniesDao companiesDao = CompaniesDao.getInstance();
		RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
		SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
		TakeOutRestaurantsDao takeOutRestaurantsDao = TakeOutRestaurantsDao.getInstance();
		FoodCartRestaurantsDao foodCartRestaurantsDao = FoodCartRestaurantsDao.getInstance();
		ReviewsDao reviewsDao = ReviewsDao.getInstance();
		RecommendationsDao recommendationsDao = RecommendationsDao.getInstance();
		ReservationsDao reservationsDao = ReservationsDao.getInstance();

		// INSERT objects from our model.
		Users user1 = new Users("b1", "password", "bruce", "chhay", "bruce@mail", "5555555");
		user1 = usersDao.create(user1);
		Users user2 = new Users("b2", "password", "bruce", "chhay", "bruce@mail", "5555555");
		user2 = usersDao.create(user2);
		Users user3 = new Users("b3", "password", "bruce", "chhay", "bruce@mail", "5555555");
		user3 = usersDao.create(user3);

		Date date = new Date();
		CreditCards creditCard1 = new CreditCards(8888123456780001L, date, "b1");
		creditCard1 = creditCardsDao.create(creditCard1);
		CreditCards creditCard2 = new CreditCards(8888123456780002L, date, "b1");
		creditCard2 = creditCardsDao.create(creditCard2);
		CreditCards creditCard3 = new CreditCards(8888123456780003L, date, "b1");
		creditCard3 = creditCardsDao.create(creditCard3);

		Companies company1 = new Companies("c1", "about c1");
		company1 = companiesDao.create(company1);
		Companies company2 = new Companies("c2", "about c2");
		company2 = companiesDao.create(company2);
		Companies company3 = new Companies("c3", "about c3");
		company3 = companiesDao.create(company3);

		Restaurants restaurant1 = new Restaurants("r1", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.AFRICAN, "street1", "street2", "seattle", "wa", 98195, company1);
		restaurant1 = restaurantsDao.create(restaurant1);
		Restaurants restaurant2 = new Restaurants("r2", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.AMERICAN, "street1", "street2", "seattle", "wa", 98195, company1);
		restaurant2 = restaurantsDao.create(restaurant2);
		Restaurants restaurant3 = new Restaurants("r3", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.ASIAN, "street1", "street2", "seattle", "wa", 98195, company1);
		restaurant3 = restaurantsDao.create(restaurant3);
		Restaurants restaurant4 = new Restaurants("r4", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.AFRICAN, "street1", "street2", "seattle", "wa", 98195, company2);
		restaurant4 = restaurantsDao.create(restaurant4);
		Restaurants restaurant5 = new Restaurants("r5", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.AMERICAN, "street1", "street2", "seattle", "wa", 98195, company2);
		restaurant5 = restaurantsDao.create(restaurant5);
		Restaurants restaurant6 = new Restaurants("r6", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.ASIAN, "street1", "street2", "seattle", "wa", 98195, company2);
		restaurant6 = restaurantsDao.create(restaurant6);
		Restaurants restaurant7 = new Restaurants("r7", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.AFRICAN, "street1", "street2", "seattle", "wa", 98195, company3);
		restaurant7 = restaurantsDao.create(restaurant7);
		Restaurants restaurant8 = new Restaurants("r8", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.AMERICAN, "street1", "street2", "seattle", "wa", 98195, company3);
		restaurant8 = restaurantsDao.create(restaurant8);
		Restaurants restaurant9 = new Restaurants("r9", "about restaurant", "menu", "hours", true,
				Restaurants.CuisineType.ASIAN, "street1", "street2", "seattle", "wa", 98195, company3);
		restaurant9 = restaurantsDao.create(restaurant9);

		SitDownRestaurants sitDownRestaurant1 = new SitDownRestaurants(1, "r1", "about restaurant", "menu", "hours",
				true, Restaurants.CuisineType.AFRICAN, "street1", "street2", "seattle", "wa", 98195, company1, 100);
		sitDownRestaurant1 = sitDownRestaurantsDao.create(sitDownRestaurant1);
		SitDownRestaurants sitDownRestaurant2 = new SitDownRestaurants(2, "r2", "about restaurant", "menu", "hours",
				true, Restaurants.CuisineType.AMERICAN, "street1", "street2", "seattle", "wa", 98195, company1, 200);
		sitDownRestaurant2 = sitDownRestaurantsDao.create(sitDownRestaurant2);
		SitDownRestaurants sitDownRestaurant3 = new SitDownRestaurants(3, "r3", "about restaurant", "menu", "hours",
				true, Restaurants.CuisineType.ASIAN, "street1", "street2", "seattle", "wa", 98195, company1, 300);
		sitDownRestaurant3 = sitDownRestaurantsDao.create(sitDownRestaurant3);

		TakeOutRestaurants takeOutRestaurant1 = new TakeOutRestaurants(4, "r4", "about restaurant", "menu", "hours",
				true, Restaurants.CuisineType.AFRICAN, "street1", "street2", "seattle", "wa", 98195, company2, 60);
		takeOutRestaurant1 = takeOutRestaurantsDao.create(takeOutRestaurant1);
		TakeOutRestaurants takeOutRestaurant2 = new TakeOutRestaurants(5, "r5", "about restaurant", "menu", "hours",
				true, Restaurants.CuisineType.AMERICAN, "street1", "street2", "seattle", "wa", 98195, company2, 90);
		takeOutRestaurant2 = takeOutRestaurantsDao.create(takeOutRestaurant2);
		TakeOutRestaurants takeOutRestaurant3 = new TakeOutRestaurants(6, "r6", "about restaurant", "menu", "hours",
				true, Restaurants.CuisineType.ASIAN, "street1", "street2", "seattle", "wa", 98195, company2, 120);
		takeOutRestaurant3 = takeOutRestaurantsDao.create(takeOutRestaurant3);

		FoodCartRestaurants foodCartRestaurant1 = new FoodCartRestaurants(7, "r7", "about restaurant", "menu", "hours",
				true, Restaurants.CuisineType.AFRICAN, "street1", "street2", "seattle", "wa", 98195, company3, true);
		foodCartRestaurant1 = foodCartRestaurantsDao.create(foodCartRestaurant1);
		FoodCartRestaurants foodCartRestaurant2 = new FoodCartRestaurants(8, "r7", "about restaurant", "menu", "hours",
				true, Restaurants.CuisineType.AFRICAN, "street1", "street2", "seattle", "wa", 98195, company3, true);
		foodCartRestaurant2 = foodCartRestaurantsDao.create(foodCartRestaurant2);
		FoodCartRestaurants foodCartRestaurant3 = new FoodCartRestaurants(9, "r7", "about restaurant", "menu", "hours",
				false, Restaurants.CuisineType.AFRICAN, "street1", "street2", "seattle", "wa", 98195, company3, true);
		foodCartRestaurant3 = foodCartRestaurantsDao.create(foodCartRestaurant3);

		Reviews review1 = new Reviews(date, "Delightful!", 5.0, user1, restaurant1);
		review1 = reviewsDao.create(review1);
		Reviews review2 = new Reviews(date, "Superb!", 5.0, user1, restaurant2);
		review2 = reviewsDao.create(review2);
		Reviews review3 = new Reviews(date, "Superb!", 5.0, user3, restaurant1);
		review3 = reviewsDao.create(review3);
		Reviews review4 = new Reviews(date, "'Not good", 1.0, user2, restaurant8);
		review4 = reviewsDao.create(review4);
		Reviews review5 = new Reviews(date, "'Not good", 1.0, user2, restaurant9);
		review5 = reviewsDao.create(review5);

		Recommendations recommendation1 = new Recommendations(user1, restaurant1);
		recommendation1 = recommendationsDao.create(recommendation1);
		Recommendations recommendation2 = new Recommendations(user1, restaurant2);
		recommendation2 = recommendationsDao.create(recommendation2);
		Recommendations recommendation3 = new Recommendations(user1, restaurant3);
		recommendation3 = recommendationsDao.create(recommendation3);
		Recommendations recommendation4 = new Recommendations(user2, restaurant1);
		recommendation4 = recommendationsDao.create(recommendation4);
		Recommendations recommendation5 = new Recommendations(user2, restaurant2);
		recommendation5 = recommendationsDao.create(recommendation5);
		Recommendations recommendation6 = new Recommendations(user2, restaurant3);
		recommendation6 = recommendationsDao.create(recommendation6);
		Recommendations recommendation7 = new Recommendations(user3, restaurant3);
		recommendation7 = recommendationsDao.create(recommendation7);
		Recommendations recommendation8 = new Recommendations(user2, restaurant4);
		recommendation8 = recommendationsDao.create(recommendation8);
		Recommendations recommendation9 = new Recommendations(user3, restaurant5);
		recommendation9 = recommendationsDao.create(recommendation9);

		Reservations reservation1 = new Reservations(date, date, 2, user1, sitDownRestaurant1);
		reservation1 = reservationsDao.create(reservation1);
		Reservations reservation2 = new Reservations(date, date, 2, user1, sitDownRestaurant2);
		reservation2 = reservationsDao.create(reservation2);
		Reservations reservation3 = new Reservations(date, date, 2, user2, sitDownRestaurant1);
		reservation3 = reservationsDao.create(reservation3);
		Reservations reservation4 = new Reservations(date, date, 2, user2, sitDownRestaurant2);
		reservation4 = reservationsDao.create(reservation4);
		Reservations reservation5 = new Reservations(date, date, 2, user3, sitDownRestaurant1);
		reservation5 = reservationsDao.create(reservation5);

		// READ.
		System.out.println("Test get methods");
		// UsersDao
		// getUserByUserName(String userName)
		Users u1 = usersDao.getUserByUserName("b1");
		System.out.format("Reading user by userName (b1): u:%s pw:%s f:%s l:%s e:%s p:%s \n",
				u1.getUserName(), u1.getPassword(),
				u1.getFirstName(), u1.getLastName(),
				u1.getEmail(), u1.getPhone());
		System.out.println();

		// CreditCardsDao
		// getCreditCardByCardNumber(long cardNumber)
		CreditCards card1 = creditCardsDao.getCreditCardByCardNumber(8888123456780001L);
		System.out.format("Reading credit card by card number (8888123456780001): cn:%s e:%s u:%s \n",
				card1.getCardNumber(), card1.getExpiration(), card1.getUserName());
		System.out.println();
		// getCreditCardsByUserName(String userName)
		List<CreditCards> cardList1 = creditCardsDao.getCreditCardsByUserName("b1");		
		for (CreditCards creditCard : cardList1) {
			System.out.format("Looping credit cards by userName (b1): cn:%s e:%s u:%s \n",
					creditCard.getCardNumber(),
					creditCard.getExpiration(),
					creditCard.getUserName());
		}
		System.out.println();
		
		// CompaniesDao: getCompanyByCompanyName(String companyName)
		Companies c1 = companiesDao.getCompanyByCompanyName("c1");
		System.out.format("Reading company by company name (c1): cn:%s a:%s \n",
				c1.getCompanyName(), c1.getAbout());
		System.out.println();
		
		// RestaurantsDao
		// getRestaurantById(int restaurantId)
		Restaurants rest1 = restaurantsDao.getRestaurantById(1);
		System.out.format("Reading restaurant by restaurantId (1): rId:%s n:%s d:%s m:%s h:%s a:%s ct:%s st1:%s st2:%s c:%s st:%s z:%s cn:%s \n",
				rest1.getRestaurantId(), rest1.getName(), rest1.getDescription(),
				rest1.getMenu(), rest1.getHours(), rest1.isActive(), rest1.getCuisineType(),
				rest1.getStreet1(), rest1.getStreet2(), rest1.getCity(), rest1.getState(),
				rest1.getZip(), rest1.getCompany().getCompanyName());
		System.out.println();
		// getRestaurantsByCuisine(Restaurants.CuisineType cuisine)
		List<Restaurants> restList1 = restaurantsDao.getRestaurantsByCuisine(Restaurants.CuisineType.AMERICAN);
		for (Restaurants restaurant : restList1) {
			System.out.format("Looping restaurants by cuisine type (AMERICAN): rId:%s n:%s d:%s m:%s h:%s a:%s ct:%s st1:%s st2:%s c:%s st:%s z:%s cn:%s \n",
					restaurant.getRestaurantId(), restaurant.getName(), restaurant.getDescription(),
					restaurant.getMenu(), restaurant.getHours(), restaurant.isActive(), restaurant.getCuisineType(),
					restaurant.getStreet1(), restaurant.getStreet2(), restaurant.getCity(), restaurant.getState(),
					restaurant.getZip(), restaurant.getCompany().getCompanyName());
		}
		System.out.println();
		// getRestaurantsByCompanyName(String companyName) 
		List<Restaurants> restList2 = restaurantsDao.getRestaurantsByCompanyName("c1");
		for (Restaurants restaurant : restList2) {
			System.out.format("Looping restaurants by company name (c1): rId:%s n:%s d:%s m:%s h:%s a:%s ct:%s st1:%s st2:%s c:%s st:%s z:%s cn:%s \n",
					restaurant.getRestaurantId(), restaurant.getName(), restaurant.getDescription(),
					restaurant.getMenu(), restaurant.getHours(), restaurant.isActive(), restaurant.getCuisineType(),
					restaurant.getStreet1(), restaurant.getStreet2(), restaurant.getCity(), restaurant.getState(),
					restaurant.getZip(), restaurant.getCompany().getCompanyName());
		}
		System.out.println();
		
		// SitDownRestaurantsDao
		// getSitDownRestaurantById(int sitDownRestaurantId)
		SitDownRestaurants sdRest1 = sitDownRestaurantsDao.getSitDownRestaurantById(1);
		System.out.format("Reading sitDownRestaurant by Id (1): rId:%s c:%s \n",
				sdRest1.getRestaurantId(), sdRest1.getCapacity());
		System.out.println();
		// getSitDownRestaurantsByCompanyName(String companyName)
		List<SitDownRestaurants> sdRestList1 = sitDownRestaurantsDao.getSitDownRestaurantsByCompanyName("c1");
		for (SitDownRestaurants restaurant : sdRestList1) {
			System.out.format("Looping sitDownRestaurants by company name (c1): rId:%s c:%s \n",
					restaurant.getRestaurantId(), restaurant.getCapacity());
		}
		System.out.println();
		
		// TakeOutRestaurantsDao
		// getTakeOutRestaurantById(int takeOutRestaurantId)
		TakeOutRestaurants toRest1 = takeOutRestaurantsDao.getTakeOutRestaurantById(4);
		System.out.format("Reading takeOutRestaurant by Id(4): rId:%s mwt:%s \n",
				toRest1.getRestaurantId(), toRest1.getMaxWaitTime());
		System.out.println();
		// getTakeOutRestaurantsByCompanyName(String companyName)
		List<TakeOutRestaurants> toRestList1 = takeOutRestaurantsDao.getTakeOutRestaurantsByCompanyName("c2");
		for (TakeOutRestaurants restaurant : toRestList1) {
			System.out.format("Looping takeOutRestaurants by company name (c2): rId:%s mwt:%s \n",
					restaurant.getRestaurantId(), restaurant.getMaxWaitTime());
		}
		System.out.println();
		
		// FoodCartRestaurantsDao
		// getFoodCartRestaurantById(int foodCartRestaurantId)
		FoodCartRestaurants fcRest1 = foodCartRestaurantsDao.getFoodCartRestaurantById(7);
		System.out.format("Reading foodCartRestaurant by Id(7): rId:%s l:%s \n",
				fcRest1.getRestaurantId(), fcRest1.isLicensed());
		System.out.println();
		//  getFoodCartRestaurantsByCompanyName(String companyName)
		List<FoodCartRestaurants> fcRestList1 = foodCartRestaurantsDao.getFoodCartRestaurantsByCompanyName("c3");
		for (FoodCartRestaurants restaurant : fcRestList1) {
			System.out.format("Looping foodCartRestaurants by company name (c3): rId:%s l:%s \n",
					restaurant.getRestaurantId(), restaurant.isLicensed());
		}
		System.out.println();
		
		// ReviewsDao
		// getReviewById(int reviewId)
		Reviews revi1 = reviewsDao.getReviewById(1);
		System.out.format("Reading review by reviewId (1): rId:%s c:%s ct:%s r:%s u:%s rstId:%s \n",
				revi1.getReviewId(), revi1.getCreated(), revi1.getContent(), revi1.getRating(),
				revi1.getUser().getUserName(), revi1.getRestaurant().getRestaurantId());
		System.out.println();
		// getReviewsByUserName(String userName)
		List<Reviews>  reviewList1 = reviewsDao.getReviewsByUserName("b1");
		for (Reviews review : reviewList1) {
			System.out.format("Looping reviews by userName (b1): rId:%s c:%s ct:%s r:%s u:%s rstId:%s \n",
					review.getReviewId(), review.getCreated(), review.getContent(), review.getRating(),
					review.getUser().getUserName(), review.getRestaurant().getRestaurantId());
		}
		System.out.println();
		// getReviewsByRestaurantId(int restaurantId)
		List<Reviews>  reviewList2 = reviewsDao.getReviewsByRestaurantId(1);
		for (Reviews review : reviewList2) {
			System.out.format("Looping reviews by restaurantId (1): rId:%s c:%s ct:%s r:%s u:%s rstId:%s \n",
					review.getReviewId(), review.getCreated(), review.getContent(), review.getRating(),
					review.getUser().getUserName(), review.getRestaurant().getRestaurantId());
		}
		System.out.println();
		
		// RecommendationsDao
		// getRecommendationById(int recommendationId)
		Recommendations reco1 = recommendationsDao.getRecommendationById(1);
		System.out.format("Reading recommendation by recommendationId (1): rId:%s u:%s rstId:%s \n",
				reco1.getRecommendationId(), 
				reco1.getUser().getUserName(), 
				reco1.getRestaurant().getRestaurantId());
		System.out.println();
		// getRecommendationsByUserName(String userName)
		List<Recommendations>  recomList1 = recommendationsDao.getRecommendationsByUserName("b1");
		for (Recommendations recommendation : recomList1) {
			System.out.format("Looping recommendations by userName (b1): rId:%s u:%s rstId:%s \n",
					recommendation.getRecommendationId(), 
					recommendation.getUser().getUserName(), 
					recommendation.getRestaurant().getRestaurantId());
		}
		System.out.println();
		// getRecommendationsByRestaurantId(int restaurantId)
		List<Recommendations>  recomList2 = recommendationsDao.getRecommendationsByRestaurantId(3);
		for (Recommendations recommendation : recomList2) {
			System.out.format("Looping recommendations by restaurantId (3): rId:%s u:%s rstId:%s \n",
					recommendation.getRecommendationId(), 
					recommendation.getUser().getUserName(), 
					recommendation.getRestaurant().getRestaurantId());
		}
		System.out.println();
		
		// ReservationsDao
		// getReservationById(int reservationId)
		Reservations resev1 = reservationsDao.getReservationById(1);
		System.out.format("Reading reservation by reservationId (1): rId:%s s:%s e:%s s:%s u:%s rstId:%s \n",
				resev1.getReservationId(),
				resev1.getStart(),
				resev1.getEnd(),
				resev1.getSize(),
				resev1.getUser().getUserName(),
				resev1.getSitDownRestaurants().getRestaurantId());
		System.out.println();
		// getReservationsByUserName(String userName)
		List<Reservations>  resevList1 = reservationsDao.getReservationsByUserName("b1");
		for (Reservations reservation : resevList1) {
			System.out.format("Reading reservation by userName (b1): rId:%s s:%s e:%s s:%s u:%s rstId:%s \n",
					reservation.getReservationId(),
					reservation.getStart(),
					reservation.getEnd(),
					reservation.getSize(),
					reservation.getUser().getUserName(),
					reservation.getSitDownRestaurants().getRestaurantId());
		}
		System.out.println();
		// getReservationsBySitDownRestaurantId(int sitDownRestaurantId)
		List<Reservations>  resevList2 = reservationsDao.getReservationsBySitDownRestaurantId(1);
		for (Reservations reservation : resevList2) {
			System.out.format("Reading reservation by SitDownRestaurantId (1): rId:%s s:%s e:%s s:%s u:%s rstId:%s \n",
					reservation.getReservationId(),
					reservation.getStart(),
					reservation.getEnd(),
					reservation.getSize(),
					reservation.getUser().getUserName(),
					reservation.getSitDownRestaurants().getRestaurantId());
		}
		System.out.println();

		// UPDATE
		// CreditCardsDao: updateExpiration(CreditCards creditCard, Date newExpiration)
		System.out.println("Test update methods");
		Date newDate = new Date();
		System.out.format("Reading credit card by card number (8888123456780001): cn:%s e:%s u:%s \n",
				card1.getCardNumber(), card1.getExpiration(), card1.getUserName());
		creditCard1 = creditCardsDao.updateExpiration(creditCard1, newDate);
		System.out.format("Reading updated credit card: Cn:%s e:%s u:%s \n",
				creditCard1.getCardNumber(),
				creditCard1.getExpiration(), 
				creditCard1.getUserName());
		System.out.println();
		
		// CompaniesDao: updateAbout(Companies company, String newAbout)
		System.out.format("Reading company by company name (c1): cn:%s a:%s \n",
				c1.getCompanyName(), c1.getAbout());
		company1 = companiesDao.updateAbout(company1, "update about company");
		System.out.format("Reading updated company: n:%s a:%s \n",
				company1.getCompanyName(), company1.getAbout());
		System.out.println();
		
		// DELETE
		usersDao.delete(user1);
		creditCardsDao.delete(creditCard1);
		companiesDao.delete(company1);
		restaurantsDao.delete(restaurant1);
		sitDownRestaurantsDao.delete(sitDownRestaurant2);
		takeOutRestaurantsDao.delete(takeOutRestaurant1);
		foodCartRestaurantsDao.delete(foodCartRestaurant1);
		reviewsDao.delete(review1);
		recommendationsDao.delete(recommendation1);
		reservationsDao.delete(reservation1);
	}
}
