package application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Categories {

	private static final Map<String, String[]> CATEGORIES = new HashMap<>();

	static {
		CATEGORIES.put("Animals", new String[]{ 
			    "dog: A domesticated carnivorous mammal.",
			    "elephant: The largest land animal.",
			    "cat: A small domesticated carnivorous mammal known for its independence and playful behavior.",
			    "lion: A large carnivorous feline known as the 'king of the jungle'.",
			    "giraffe: The tallest land animal with a long neck and legs.",
			    "penguin: A flightless seabird found primarily in the Southern Hemisphere.",
			    "dolphin: An intelligent marine mammal known for its playful behavior and complex communication.",
			    "tiger: The largest species of the cat family, recognizable by its pattern of dark vertical stripes on reddish-orange fur.",
			    "koala: An arboreal herbivorous marsupial native to Australia, known for its teddy bear-like appearance.",
			    "kangaroo: A large marsupial from Australia with powerful hind legs for jumping.",
			    "panda: A bear native to south central China, known for its distinctive black-and-white coloring and love of bamboo.",
			    "zebra: An African wild horse with distinctive black-and-white striped coats.",
			    "rhinoceros: A large, thick-skinned herbivorous mammal with one or two horns on its snout.",
			    "sloth: A slow-moving arboreal mammal native to Central and South America, known for hanging upside down in trees."
			});

		CATEGORIES.put("Flowers", new String[]{ 
			    "rose: A type of flowering shrub.",
			    "tulip: A bulbous spring-flowering plant.",
			    "daisy: A small grassland plant that has flowers with a yellow disk and white rays.",
			    "sunflower: A large, tall plant with a bright yellow flower head and edible seeds.",
			    "orchid: A diverse and widespread family of flowering plants with blooms that are often colorful and fragrant.",
			    "lily: A genus of flowering plants known for their large, prominent flowers.",
			    "daffodil: A bulbous plant with bright yellow flowers that bloom in the spring.",
			    "marigold: A bright orange or yellow flowering plant often used in gardens and as a companion plant.",
			    "jasmine: A genus of shrubs and vines known for their fragrant white or yellow flowers.",
			    "lavender: A small aromatic evergreen shrub with bluish-purple flowers, often used in perfumes and aromatherapy.",
			    "peony: A flowering plant known for its large, colorful blooms and long lifespan.",
			    "chrysanthemum: A plant of the daisy family with brightly colored ornamental flowers.",
			    "hibiscus: A genus of flowering plants in the mallow family, known for their large, trumpet-shaped flowers.",
			    "poppy: A flowering plant known for its bright red or orange flowers and seed pods.",
			    "azalea: A flowering shrub known for its vibrant blooms, often found in gardens."
			});

		CATEGORIES.put("Vegetables", new String[]{ 
			    "carrot: A tapering orange-colored root.",
			    "broccoli: A green vegetable that looks like a tree.",
			    "spinach: A leafy green vegetable rich in iron and vitamins.",
			    "potato: A starchy tuber that is a staple food in many cultures.",
			    "tomato: A red or yellow pulpy fruit eaten as a vegetable, often used in salads and cooking.",
			    "cucumber: A long, green-skinned fruit with watery flesh, typically eaten raw in salads.",
			    "onion: A bulbous plant with a strong taste and smell, commonly used in cooking.",
			    "lettuce: A leafy green vegetable commonly used in salads and sandwiches.",
			    "zucchini: A summer squash that is usually dark or light green, often used in cooking.",
			    "garlic: A pungent bulb used as a seasoning and for its medicinal properties.",
			    "cabbage: A leafy green or purple biennial plant grown as an annual vegetable crop.",
			    "peas: Small, round green seeds that are eaten as a vegetable, usually cooked or used in soups and salads.",
			    "cauliflower: A white or purple flowering vegetable that resembles broccoli, often used in cooking.",
			    "eggplant: A glossy purple or white fruit used as a vegetable, known for its spongy texture."
			});

	}

	public static Set<String> getCategories() {
		return CATEGORIES.keySet();
	}

	public static String[] getWordsWithHints(String category) {
		return CATEGORIES.get(category);
	}
}
