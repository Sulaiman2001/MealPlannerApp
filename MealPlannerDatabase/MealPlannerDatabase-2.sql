-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 22, 2024 at 05:03 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `MealPlannerDatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `custom_ingredients`
--

CREATE TABLE `custom_ingredients` (
  `custom_ingredient_id` int(11) NOT NULL,
  `custom_user_id` int(11) NOT NULL,
  `ingredient_name` varchar(255) NOT NULL,
  `amount` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `favourites`
--

CREATE TABLE `favourites` (
  `favourites_id` int(11) NOT NULL,
  `user_favourite_id` int(11) NOT NULL,
  `meal_favourite_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `favourites`
--

INSERT INTO `favourites` (`favourites_id`, `user_favourite_id`, `meal_favourite_id`) VALUES
(18, 19, 1),
(19, 19, 2);

-- --------------------------------------------------------

--
-- Table structure for table `ingredients`
--

CREATE TABLE `ingredients` (
  `ingredient_id` int(11) NOT NULL,
  `meal_ingredient_id` int(11) NOT NULL,
  `ingredient_name` varchar(255) NOT NULL,
  `value` int(11) NOT NULL,
  `unit` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ingredients`
--

INSERT INTO `ingredients` (`ingredient_id`, `meal_ingredient_id`, `ingredient_name`, `value`, `unit`) VALUES
(1, 1, 'olive oil', 15, 'ml'),
(2, 1, 'smoked streaky bacon', 4, 'rashers'),
(3, 1, 'onions', 2, 'medium size'),
(4, 1, 'carrots', 2, 'carrots'),
(5, 1, 'celery', 2, 'sticks'),
(6, 1, 'garlic', 2, 'cloves(s)'),
(7, 1, 'rosemary', 3, 'leaves'),
(8, 1, 'beef mince', 500, 'g'),
(9, 1, 'tinned plum tomatoes', 800, 'g'),
(10, 1, 'basil leaves', 1, 'pack'),
(11, 1, 'dried oregano', 15, 'ml'),
(12, 1, 'fresh bay leaves', 2, 'leaves'),
(13, 1, 'tomato puree', 30, 'ml'),
(14, 1, 'beef stock cube', 1, 'cube'),
(15, 1, 'red chilli', 1, 'chilli'),
(16, 1, 'cherry tomatoes', 6, 'tomatoes'),
(17, 2, 'sesame oil', 22, 'ml'),
(18, 2, 'low salt soy sauce', 15, 'ml'),
(19, 2, 'ginger', 1, 'ginger'),
(20, 2, 'garlic', 1, 'clove(s)'),
(21, 2, 'honey', 15, 'ml'),
(22, 2, 'sweet potatoes', 2, 'sweet potatoes'),
(23, 2, 'lime', 1, 'lime'),
(24, 2, 'salmon fillets', 2, 'fillets'),
(25, 2, 'purple sprouting broccoli', 250, 'g'),
(26, 2, 'sesame seeds', 1, 'tbsp'),
(27, 2, 'red chilli', 1, 'chilli'),
(28, 3, 'dried porcini mushrooms', 400, 'g'),
(29, 3, 'dried brown lentils', 200, 'g'),
(30, 3, 'rosemary', 22, 'ml'),
(31, 3, 'large onions', 2, 'onions'),
(32, 3, 'chestnut baby button mushrooms', 150, 'g'),
(33, 3, 'garlic', 4, 'cloves'),
(34, 3, 'vegetable bouillon powder', 30, 'ml'),
(35, 3, 'large carrots', 2, 'carrots'),
(36, 3, 'celery', 2, 'sticks'),
(37, 3, 'potatoes', 500, 'g'),
(38, 3, 'cavolo nero', 200, 'g'),
(39, 4, 'unsalted butter', 50, 'g'),
(40, 4, 'chestnut mushrooms', 500, 'g'),
(41, 4, 'sunflower oil', 74, 'ml'),
(42, 4, 'cumin seeds', 15, 'ml'),
(43, 4, 'fennel seeds', 15, 'ml'),
(44, 4, 'large onion', 1, 'onion'),
(45, 4, 'garlic', 4, 'clove(s)'),
(46, 4, 'ground ginger', 15, 'ml'),
(47, 4, 'ground tumeric', 4, 'ml'),
(48, 4, 'kashmiri chilli powder', 7, 'ml'),
(49, 4, 'garam masala', 7, 'ml'),
(50, 4, 'can chopped tomatoes', 400, 'g'),
(51, 4, 'caster sugar', 1, 'tbsp'),
(52, 4, 'full fat greek yogurt', 30, 'ml'),
(53, 4, 'chopped coriander', 2, 'tbsp'),
(54, 4, 'rice', 360, 'g'),
(55, 5, 'self-raising flour', 350, 'g'),
(56, 5, 'baking powder', 1, 'tbsp'),
(57, 5, 'ripe bananas', 4, 'bananas'),
(58, 5, 'eggs', 2, 'egg(s)'),
(59, 5, 'vanilla extract', 15, 'ml'),
(60, 5, 'whole milk butter', 250, 'ml'),
(61, 6, 'tamari', 1, 'tbsp'),
(62, 6, 'medium curry powder', 1, 'tbsp'),
(63, 6, 'ground cumin', 0, 'tbsp'),
(64, 6, 'garlic', 1, 'clove(s)'),
(65, 6, 'clear honey', 1, 'tbsp'),
(66, 6, 'skinless chicken breast fillets', 2, 'breasts'),
(67, 6, 'crunchy peanut butter', 1, 'tbsp'),
(68, 6, 'sweet chilli sauce', 1, 'tbsp'),
(69, 6, 'lime juice', 1, 'tbsp'),
(70, 6, 'little gem lettuce', 2, 'hearts'),
(71, 6, 'cucumber', 0, 'of a cucumber'),
(72, 6, 'banana', 1, 'banana'),
(73, 6, 'pomegranate', 1, 'pomegranate'),
(74, 7, 'vegetable oil', 14, 'ml'),
(75, 7, 'butter', 50, 'g'),
(76, 7, 'milk', 250, 'ml'),
(77, 7, 'self-raising flour', 225, 'g'),
(78, 7, 'eggs', 1, 'egg(s)'),
(79, 8, 'olive oil', 15, 'ml'),
(80, 8, 'small onion', 1, 'onion'),
(81, 8, 'garlic', 1, 'clove(s)'),
(82, 8, 'tumeric', 1, 'tbsp'),
(83, 8, 'cumin', 1, 'tbsp'),
(84, 8, 'sweet smoked paprika', 1, 'tbsp'),
(85, 8, 'extra firm tofu', 200, 'g'),
(86, 8, 'cherry tomatoes', 100, 'g'),
(87, 8, 'parsley', 1, 'bunch'),
(88, 9, 'cucumber', 1, 'cucumber'),
(89, 9, 'greek yogurt', 250, 'g'),
(90, 9, 'chicken breast', 500, 'g'),
(91, 9, 'wholemeal wraps', 4, 'wraps'),
(92, 9, 'large ripe tomatoes', 4, 'tomatoes'),
(93, 9, 'olive oil', 30, 'ml'),
(94, 10, 'easy-cook brown rice', 200, 'g'),
(95, 10, 'rapeseed oil', 15, 'ml'),
(96, 10, 'red pepper', 2, 'peppers'),
(97, 10, 'garlic', 3, 'clove(s)'),
(98, 10, 'smoked paprika', 2, 'tbsp'),
(99, 10, 'ground coriander', 1, 'tbsp'),
(100, 10, 'cumin seeds', 2, 'tsp'),
(101, 10, 'passata', 500, 'ml'),
(102, 10, 'vegetable stock', 500, 'ml'),
(103, 10, 'red lentils', 150, 'g'),
(104, 10, 'thyme leaves', 1, 'tsp'),
(105, 10, 'can black beans', 400, 'g'),
(106, 10, 'avocados', 2, 'avocados'),
(107, 10, 'lime', 1, 'lime'),
(108, 10, 'small red onion', 1, 'onion'),
(109, 10, 'red chilli', 1, 'chilli'),
(110, 10, 'vine tomatoes', 2, 'tomatoes'),
(111, 10, 'pack of coriander', 30, 'g'),
(112, 11, 'dried ancho chillies', 2, 'chillies'),
(113, 11, 'black peppercorns', 2, 'tsp'),
(114, 11, 'cumin seeds', 2, 'tbsp'),
(115, 11, 'coriander seeds', 2, 'tbsp'),
(116, 11, 'smoked paprika', 2, 'tsp'),
(117, 11, 'dried oregano', 1, 'tbsp'),
(118, 11, 'vegetable oil', 51, 'ml'),
(119, 11, 'braising steak', 2, 'kg'),
(120, 11, 'onion', 2, 'onions'),
(121, 11, 'garlic', 6, 'clove(s)'),
(122, 11, 'tomato puree', 2, 'tbsp'),
(123, 11, 'smooth peanut butter', 1, 'tbsp'),
(124, 11, 'instant espresso powder', 1, 'tsp'),
(125, 11, 'apple cider vinegar', 2, 'tbsp'),
(126, 10, 'beef stock', 1, 'l'),
(127, 11, 'bay leaves', 2, 'leaves'),
(128, 11, 'cinnamon stick', 1, 'stick'),
(129, 11, 'dark chocolate', 25, 'g'),
(130, 12, 'olive oil', 15, 'ml'),
(131, 12, 'onion', 1, 'onion'),
(132, 12, 'thyme spigs', 3, 'leaves'),
(133, 12, 'garlic', 2, 'cloves'),
(134, 12, 'chicken breasts', 350, 'g'),
(135, 12, 'chestnut mushrooms', 250, 'g'),
(136, 12, 'chicken stock', 300, 'ml'),
(137, 12, 'creme fraiche', 100, 'g'),
(138, 12, 'wholegrain mustard', 1, 'tbsp'),
(139, 12, 'kale', 100, 'g'),
(140, 12, 'cornflour', 2, 'tbsp'),
(142, 12, 'pack puff pastry', 375, 'g'),
(143, 12, 'eggs', 1, 'egg(s)'),
(144, 13, 'brown basmati rice', 240, 'g'),
(145, 13, 'rapeseed oil', 30, 'ml'),
(146, 13, 'large onion', 1, 'onion'),
(147, 13, 'cinnamon stick', 1, 'stick'),
(148, 13, 'red chilli', 1, 'chilli'),
(149, 13, 'garlic', 3, 'clove(s)'),
(150, 13, 'ginger', 20, 'g'),
(151, 13, 'cumin seeds', 2, 'tsp'),
(152, 13, 'red pepper', 1, 'pepper'),
(153, 13, 'large aubergine', 1, 'aubergine'),
(154, 13, 'curry powder', 2, 'tbsp'),
(155, 13, 'can chopped tomatoes', 400, 'g'),
(156, 13, 'vegan bouillon powder', 2, 'tsp'),
(157, 13, 'cauliflower', 2, 'florets'),
(158, 13, 'coriander', 30, 'g'),
(159, 13, 'flame raisins', 40, 'g'),
(160, 13, 'unsalted cashew nuts', 50, 'g'),
(161, 14, 'leg of lamb', 2, 'kg'),
(162, 14, 'rosemary', 1, 'bunch'),
(163, 14, 'garlic', 5, 'cloves'),
(164, 14, 'brown anchovy fillets', 6, 'anchovies'),
(165, 14, 'olive oil', 30, 'ml'),
(166, 14, 'onion', 2, 'onions'),
(167, 15, 'chipotle paste', 1, 'tsp'),
(168, 15, 'rapeseed oil', 15, 'ml'),
(169, 15, 'kale', 50, 'g'),
(170, 15, 'eggs', 1, 'egg(s)'),
(171, 15, 'cherry tomatoes', 7, 'tomatoes'),
(172, 15, 'small avocado', 1, 'avocado'),
(173, 15, 'wholemeal wraps', 1, 'wraps'),
(174, 16, 'eggs', 2, 'egg(s)'),
(175, 16, 'natural low-fat yogurt', 150, 'ml'),
(176, 16, 'rapeseed oil', 50, 'ml'),
(177, 16, 'apple sauce', 100, 'g'),
(178, 16, 'banana', 1, 'bananas'),
(179, 16, 'vanilla extract', 1, 'tsp'),
(180, 16, 'wholemeal flour', 200, 'g'),
(181, 16, 'honey', 4, 'tbsp'),
(182, 16, 'rolled oats', 50, 'g'),
(183, 16, 'baking powder', 2, 'tsp'),
(184, 16, 'bicarbonate of soda', 2, 'tsp'),
(185, 16, 'cinnamon', 2, 'tsp'),
(186, 16, 'blueberry', 100, 'g'),
(187, 16, 'mixed seed', 2, 'tbsp');

-- --------------------------------------------------------

--
-- Table structure for table `meal`
--

CREATE TABLE `meal` (
  `meal_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `meal_type` varchar(255) NOT NULL,
  `ingredients` text NOT NULL,
  `recipe` text NOT NULL,
  `time_to_cook` int(11) NOT NULL,
  `imagePath` text NOT NULL,
  `serves` int(11) NOT NULL,
  `vegan` tinyint(1) NOT NULL,
  `vegetarian` tinyint(1) NOT NULL,
  `calories` int(11) NOT NULL,
  `favourite_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `meal`
--

INSERT INTO `meal` (`meal_id`, `title`, `meal_type`, `ingredients`, `recipe`, `time_to_cook`, `imagePath`, `serves`, `vegan`, `vegetarian`, `calories`, `favourite_count`) VALUES
(1, 'Spaghetti bolognese', 'Dinner', '1 tbsp olive oil\n4 rashers smoked streaky bacon, finely chopped\n2 medium onions, finely chopped\n2 carrots, trimmed and finely chopped\n2 celery sticks, finely chopped\n2 garlic cloves finely chopped\n2-3 sprigs rosemary leaves picked and finely chopped\n500g beef mince\nFor the bolognese sauce\n2 x 400g tins plum tomatoes\nsmall pack basil leaves picked, ¾ finely chopped and the rest left whole for garnish\n1 tsp dried oregano\n2 fresh bay leaves\n2 tbsp tomato purée\n1 beef stock cube\n1 red chilli deseeded and finely chopped (optional)\n125ml red wine\n6 cherry tomatoes sliced in half\nTo season and serve\n75g parmesan grated, plus extra to serve\n400g spaghetti', 'STEP 1\nPut a large saucepan on a medium heat and add 1 tbsp olive oil.\n\nSTEP 2\nAdd 4 finely chopped bacon rashers and fry for 10 mins until golden and crisp.\n\nSTEP 3\nReduce the heat and add the 2 onions, 2 carrots, 2 celery sticks, 2 garlic cloves and the leaves from 2-3 sprigs rosemary, all finely chopped, then fry for 10 mins. Stir the veg often until it softens.\n\nSTEP 4\nIncrease the heat to medium-high, add 500g beef mince and cook stirring for 3-4 mins until the meat is browned all over.\n\nSTEP 5\nAdd 2 tins plum tomatoes, the finely chopped leaves from ¾ small pack basil, 1 tsp dried oregano, 2 bay leaves, 2 tbsp tomato purée, 1 beef stock cube, 1 deseeded and finely chopped red chilli (if using), 125ml red wine and 6 halved cherry tomatoes. Stir with a wooden spoon, breaking up the plum tomatoes.\n\nSTEP 6\nBring to the boil, reduce to a gentle simmer and cover with a lid. Cook for 1 hr 15 mins stirring occasionally, until you have a rich, thick sauce.\n\nSTEP 7\nAdd the 75g grated parmesan, check the seasoning and stir.\n\nSTEP 8\nWhen the bolognese is nearly finished, cook 400g spaghetti following the pack instructions.\n\nSTEP 9\nDrain the spaghetti and either stir into the bolognese sauce, or serve the sauce on top. Serve with more grated parmesan, the remaining basil leaves and crusty bread, if you like.', 110, 'spag_bol.png', 2, 0, 0, 624, 4),
(2, 'Salmon, broccoli and sweet potato mash', 'Dinner', '1 ½ tbsp sesame oil\n1 tbsp low-salt soy sauce\nthumb-sized piece ginger, grated\n1 garlic clove, crushed\n1 tsp honey\n2 sweet potatoes, scrubbed and cut into wedges\n1 lime, cut into wedges\n2 boneless skinless salmon fillets\n250g purple sprouting broccoli\n1 tbsp sesame seeds\n1 red chilli, thinly sliced (deseeded if you don\'t like it too hot)', 'STEP 1\r\nHeat oven to 200C/180 fan/ gas 6 and line a baking tray with parchment. Mix together 1/2 tbsp sesame oil, the soy, ginger, garlic and honey. Put the sweet potato wedges, skin and all, into a glass bowl with the lime wedges. Cover with cling film and microwave on high for 12-14 mins until completely soft.\r\n\r\nSTEP 2\r\nMeanwhile, spread the broccoli and salmon out on the baking tray. Spoon over the marinade and season. Roast in the oven for 10-12 mins, then sprinkle over the sesame seeds.\r\n\r\nSTEP 3\r\nRemove the lime wedges and roughly mash the sweet potato using a fork. Mix in the remaining sesame oil, the chilli and some seasoning. Divide between plates, along with the salmon and broccoli.', 15, 'salmon_broccoli_sweet_potato_mash.png', 2, 0, 0, 463, 3),
(3, 'Lentil stew', 'Dinner', '40g dried porcini mushrooms, roughly chopped\n200g dried brown lentils\n1 ½ tbsp chopped rosemary\n3 tbsp rapeseed oil\n2 large onions, roughly chopped\n150g chestnut baby button mushrooms\n4 garlic cloves, finely grated\n2 tbsp vegetable bouillon powder\n2 large carrots (350g), cut into chunks\n3 celery sticks (165g), chopped\n500g potatoes, cut into chunks\n200g cavolo nero, shredded', 'STEP 1\r\nCover the mushrooms in boiling water and leave to soak for 10 mins. Boil the lentils in a pan with plenty of water for 10 mins. Drain and rinse, then tip into a pan with the dried mushrooms and soaking water (don’t add the last bit of the liquid as it can contain some grit), rosemary and 2 litres water. Season, cover and simmer for 20 mins.\r\n\r\nSTEP 2\r\nMeanwhile, heat the oil in a large pan and fry the onions for 5 mins. Stir in the fresh mushrooms and garlic and fry for 5 mins more. Stir in the lentil mixture and bouillon powder, then add the carrots, celery and potatoes. Cover and cook for 20 mins, stirring often, until the veg and lentils are tender, topping up the water level if needed.\r\n\r\nSTEP 3\r\nRemove any tough stalks from the cavolonero, then add to the pan and cover and cook for 5 mins more. If you\'re following our Healthy Diet Plan, serve half in bowls, then chill the rest to eat another day. Will keep in the fridge for two to three days. Reheat in a pan until hot.', 60, 'lentil_stew.png', 4, 1, 1, 485, 1),
(4, 'Mushroom curry', 'Dinner', '50g unsalted butter\n500g chestnut mushrooms, quartered\n4-6 tbsp sunflower oil\n1 tsp cumin seeds\n1 tsp fennel seeds\n1 large onion, finely chopped\n4 garlic cloves, finely chopped\n1 tsp ground ginger\n¼ tsp ground turmeric\n½ tsp Kashmiri chilli powder\n½ tsp garam masala\n400g can chopped tomatoes\n1 tsp caster sugar\n2 tbsp thick, full-fat Greek yogurt\n2 tbsp chopped coriander\ncooked rice or naan, to serve', 'STEP 1\r\nMelt the butter in a large wok, karahi or non-stick frying pan over a medium-high heat and cook the mushrooms for 10 mins, or until any moisture has evaporated and they’re starting to brown. Transfer to a bowl and set aside.\r\n\r\nSTEP 2\r\nHeat the oil in the same pan over a medium-high heat and fry the cumin and fennel seeds, stirring continuously for about 30 seconds until they release a nutty aroma. Stir in the onion and cook for 12-15 mins until golden. Reduce the heat to medium, then add the garlic and continue frying for 1 min. Add the ginger, turmeric, chilli powder and garam masala, followed by the tomatoes and sugar. Cook, uncovered, for 5-7 mins, or until the masala thickens and a layer of oil forms around the edge of the pan.\r\n\r\nSTEP 3\r\nSpoon the yogurt into a small bowl, add a small ladleful of the hot masala and mix well before stirring the yogurt into the pan of masala. Pour in 100ml hot water and simmer for 3-4 mins until the curry has the consistency of double cream. Season to taste, then return the mushrooms to the pan and stir to warm through. Scatter with the chopped coriander and serve with boiled rice or naan, if you like.', 40, 'mushroom_curry.png', 4, 0, 1, 302, 1),
(5, 'Easy banana pancakes', 'Breakfast', '350g self-raising flour\n1 tsp baking powder \n2 very ripe bananas\n2 medium eggs\n1 tsp vanilla extract\n250ml whole milk\nbutter, for frying\n\nTo serve:\n2 just ripe bananas, sliced\nmaple syrup (optional)\npecan halves, toasted and roughly chopped (optional)', 'STEP 1\r\nSieve the flour, baking powder and a generous pinch of salt into a large bowl. In a separate mixing bowl, mash the very ripe bananas with a fork until smooth, then whisk in the eggs, vanilla extract and milk. Make a well in the centre of the dry ingredients, tip in the wet ingredients and swiftly whisk together to create a smooth, silky batter.\r\n\r\nSTEP 2\r\nHeat a little knob of butter in a large non-stick pan over a medium heat. Add 2-3 tbsp of the batter to the pan and cook for several minutes, or until small bubbles start appearing on the surface. Flip the pancake over and cook for 1-2 mins on the other side. Repeat with the remaining batter, keeping the pancakes warm in a low oven.\r\n\r\nSTEP 3\r\nStack the pancakes on plates and top with the banana slices, a glug of sticky maple syrup and a handful of pecan nuts, if you like.', 10, 'banana_pancakes.png', 12, 0, 1, 484, 0),
(6, 'Chicken satay salad', 'Lunch', '1 tbsp tamari\n1 tsp medium curry powder\n¼ tsp ground cumin\n1 garlic clove, finely grated\n1 tsp clear honey\n2 skinless chicken breast fillets (or use turkey breast)\n1 tbsp crunchy peanut butter (choose a sugar-free version with no palm oil, if possible)\n1 tbsp sweet chilli sauce\n1 tbsp lime juice\nsunflower oil, for wiping the pan\n2 Little Gem lettuce hearts, cut into wedges\n¼ cucumber, halved and sliced\n1 banana shallot, halved and thinly sliced\ncoriander, chopped\nseeds from ½ pomegranate', 'STEP 1\r\nPour the tamari into a large dish and stir in the curry powder, cumin, garlic and honey. Mix well. Slice the chicken breasts in half horizontally to make 4 fillets in total, then add to the marinade and mix well to coat. Set aside in the fridge for at least 1 hr, or overnight, to allow the flavours to penetrate the chicken.\r\n\r\nSTEP 2\r\nMeanwhile, mix the peanut butter with the chilli sauce, lime juice, and 1 tbsp water to make a spoonable sauce. When ready to cook the chicken, wipe a large non-stick frying pan with a little oil. Add the chicken and cook, covered with a lid, for 5-6 mins on a medium heat, turning the fillets over for the last min, until cooked but still moist. Set aside, covered, to rest for a few mins.\r\n\r\nSTEP 3\r\nWhile the chicken rests, toss the lettuce wedges with the cucumber, shallot, coriander and pomegranate, and pile onto plates. Spoon over a little sauce. Slice the chicken, pile on top of the salad and spoon over the remaining sauce. Eat while the chicken is still warm.', 10, 'chicken_satay_salad.png', 2, 0, 0, 353, 0),
(7, 'Classic waffles', 'Breakfast', '1 tbsp sunflower or vegetable oil\n50g butter, melted and cooled\n250ml milk\n225g self-raising flour\n1 egg', 'STEP 1\r\nCrack the egg (for fluffier waffles, use only the yolk at this stage) into a large bowl, then tip in the flour and a generous pinch of salt. Add the sugar, if using, then gradually whisk in the milk followed by the melted butter until smooth. Whisk in the vanilla, if using. If you\'ve chosen to make fluffier waffles, whisk the egg white to soft peaks, then gently fold this into the batter. Alternatively, make the batter by blitzing all the ingredients together using a blender or hand blender. Can be made 1-2 hrs ahead and chilled.\r\n\r\nSTEP 2\r\nHeat a waffle maker following the manufacturer\'s instructions, brush with a little of the oil, then ladle in enough batter to just cover the surface. Cook following the manufacturer\'s instructions (usually 5-6 mins) until the waffles are golden brown and crisp. Serve immediately or keep warm in a low oven while you make the rest. Drizzle with maple syrup or sprinkle with icing sugar, if you like.', 25, 'waffles.png', 5, 0, 1, 267, 0),
(8, 'Tofu scramble', 'Breakfast', '1 tbsp olive oil\n1 small onion, finely sliced\n1 large garlic clove, crushed\n½ tsp turmeric\n1 tsp ground cumin\n½ tsp sweet smoked paprika\n280g extra firm tofu\n100g cherry tomatoes, halved\n½ small bunch parsley, chopped', 'STEP 1\r\nHeat the oil in a frying pan over a medium heat and gently fry the onion for 8 -10 mins or until golden brown and sticky. Stir in the garlic, turmeric, cumin and paprika and cook for 1 min.\r\n\r\nSTEP 2\r\nRoughly mash the tofu in a bowl using a fork, keeping some pieces chunky. Add to the pan and fry for 3 mins. Raise the heat, then tip in the tomatoes, cooking for 5 mins more or until they begin to soften. Fold the parsley through the mixture. Serve on its own or with toasted rye bread (not gluten-free), if you like.', 25, 'tofu_scramble.png', 2, 1, 1, 225, 0),
(9, 'Chicken & tzatziki wraps', 'Lunch', '1 cucumber, three-quarters deseeded and coarsely grated, the rest halved and sliced\n250g Greek yogurt\n500g chicken breast, thinly sliced\n2tbsp olive oil\n4 wholemeal wraps\n4 large ripe tomatoes, thinly sliced', 'STEP 1\r\nFor the tzatziki, tip the grated cucumber and yogurt into a bowl, mix well and season. Set aside. Season the chicken with salt and pepper and rub with 1 tbsp of the olive oil. Heat the remaining oil in a pan over a medium heat. Cook the chicken for 8-10 mins until cooked through and golden brown.\r\n\r\nSTEP 2\r\nWarm the wraps in a dry pan or microwave. Spread 2 tbsp of the tzatziki onto each wrap, top with the chicken, tomatoes and sliced cucumber. Season with a little more pepper, if you like, then fold the sides of the wrap over the filling, roll up tightly and serve.', 20, 'Chicken_tzatziki_wraps.png', 4, 0, 0, 356, 0),
(10, 'Spicy red lentil chilli with guacamole & rice', 'Lunch', '200g easy-cook brown rice\n1 tbsp rapeseed oil\n2 peppers (any colour), deseeded and finely chopped\n3 garlic cloves, finely grated\n2 tbsp smoked paprika\n1 tbsp ground coriander\n2 tsp cumin seeds\n500ml passata\n500ml vegetable stock, made with 2 tsp vegetable bouillon powder\n150g red lentils\n1 tsp thyme leaves\n400g can black beans\n2 avocados, peeled, stoned, halved and mashed or cut into cubes\n1 lime, juiced\n1 small red onion, finely chopped\n1 red chilli, deseeded and finely chopped\n2 vine tomatoes, chopped\n⅓ x 30g pack of coriander, chopped', 'STEP 1\r\nHeat the oil in a large pan over a medium heat and fry the peppers for 5 mins, stirring frequently. Add the garlic and spices, stir briefly, then tip in the passata and stock. Stir in the lentils and thyme, then cover and simmer for 15-20 mins until pulpy. Tip in the beans along with the liquid from the can, then re-cover and simmer for another 10-15 mins.\r\n\r\nSTEP 2\r\nCook the rice following pack instructions. Combine half of the guacamole ingredients. Serve half the rice and chilli between two people, topped with the guacamole. Cool and chill the rest to eat another day. To serve, reheat the chilli in a pan over a low heat with a splash of water until piping hot. Reheat the rice in the microwave. Combine the rest of the guacamole ingredients to make a fresh batch, and serve on top of the chilli and rice. Will keep chilled for up to four days.', 70, 'Lentil_chilli.png', 4, 1, 1, 542, 0),
(11, 'Next level chilli con carne', 'Dinner', '2 dried ancho chillies\n2 tsp black peppercorns\n2 tbsp cumin seeds\n2 tbsp coriander seeds\n2 tsp smoked paprika\n1 tbsp dried oregano\n3 tbsp vegetable oil\n1 ½kg braising steak, cut into 4cm cubes – meat from the brisket, short rib, blade or chuck steak are all good\n2 onions, finely chopped\n6 garlic cloves, minced\n2 tbsp tomato purée\n1 tbsp smooth peanut butter\n½ tsp instant espresso powder\n2 tbsp apple cider vinegar\n1l beef or chicken stock\n2 bay leaves\nsmall piece of cinnamon stick\n2 tbsp semolina, polenta or Mexican masa flour\n25g dark chocolate (70-80% cocoa solids)', 'STEP 1\nHeat oven to 140C/120C fan/gas 1. Over a high heat, toast the whole chillies on all sides until you can smell them cooking, then remove and set aside. Keep the pan on the heat and toast the peppercorns, cumin seeds and coriander seeds until they just start to change colour, then grind to a powder using a pestle and mortar or spice grinder. Mix with the smoked paprika and oregano (this is a standard tex-mex seasoning), then set aside.\n\nSTEP 2\nReturn the casserole to the heat, add half the oil and heat until shimmering. Fry the beef in batches, adding more oil if you need to, until it’s browned on all sides, then set aside. Fry the onions in the pan over a low heat for about 8 mins until soft and golden, then add the garlic and cook for 1 min more. Working fast, add the meat and juices, the spice mix, tomato purée, peanut butter and coffee to the pan and cook for 2-3 mins, stirring to coat the meat until everything is thick and gloopy, then pour in the vinegar and stock.\n\nSTEP 3\nAdd the toasted chillies back into the casserole, along with the bay leaves, cinnamon and some salt. Bring to a simmer and stir well, then cover with the lid and cook in the oven for 3hrs, stirring occasionally, until the meat is very tender but not falling apart. Take the casserole out of the oven, put back on the stove and remove the lid. Simmer the sauce for 5 mins, then stir in the semolina flour and simmer for 2-3 mins more. Finely grate over the chocolate, stir through with the beans and simmer for a minute more to heat through. Fish out the chillies, then leave everything to rest for at least 15 mins.\n\nSTEP 4\nBring the pan to the table. Serve with bowls of accompaniments and the chilli paste (see tip below) to add heat.', 205, 'chilli_con_carne.png', 8, 0, 0, 463, 1),
(12, 'Chicken, kale & mushroom pot pie', 'Dinner', '1 tbsp olive oil\n1 large onion, finely chopped\n3 thyme sprigs, leaves picked\n2 garlic cloves, crushed\n350g chicken breasts, cut into small chunks\n250g chestnut mushrooms, sliced\n300ml chicken stock\n100g crème fraîche\n1 tbsp wholegrain mustard\n100g kale\n2 tsp cornflour, mixed with 1 tbsp cold water\n375g pack puff pastry, rolled into a circle slightly bigger than your dish\n1 egg yolk, to glaze', 'STEP 1\r\nHeat ½ tbsp oil over a gentle heat in a flameproof casserole dish. Add the onion and cook for 5 mins until softening. Scatter over the thyme and garlic, and stir for 1 min. Turn up the heat and add the chicken, frying until golden but not fully cooked. Add the mushrooms and the remaining oil. Heat oven to 200C/180 fan/gas 6.\r\n\r\nSTEP 2\r\nAdd the stock, crème fraîche, mustard and kale, and season well. Add the cornflour mixture and stir until thickened a little.\r\n\r\nSTEP 3\r\nRemove from the heat and cover with the puff pastry lid, pressing into the sides of the casserole dish. Slice a cross in the centre and glaze with the egg. Bake for 30 mins until the pastry is puffed up and golden.', 50, 'chicken_pot_pie.png', 4, 0, 0, 673, 0),
(13, 'Vegan biryani', 'Dinner', '240g brown basmati rice\n1½ tbsp rapeseed oil\n1 large onion (220g), finely chopped\n1 cinnamon stick\n1 red chilli, deseeded and finely chopped (optional)\n3 large garlic cloves, finely chopped\n20g fresh ginger, peeled and finely chopped\n1½ tsp cumin seeds\n1 large red pepper, deseeded and roughly chopped\n1 large aubergine (320g), cut into cubes\n2 tbsp curry powder\n400g can chopped tomatoes\n2 tsp vegan bouillon powder\n320g small cauliflower florets\n30g coriander, stems and leaves separated and chopped\n40g flame raisins\n50g unsalted cashew nuts, toasted', 'STEP 1\r\nRinse the rice until the water runs clear, then cook in a pan of fresh cold water following pack instructions for about 20 mins, or until almost tender.\r\n\r\nSTEP 2\r\nMeanwhile, heat the oil in a large, deep frying pan over a medium heat and stir in the onion, cinnamon stick, chilli, garlic and ginger so they’re coated in the oil. Scatter over the cumin seeds, cover and cook for 5 mins.\r\n\r\nSTEP 3\r\nStir well, then add the pepper and aubergine, and cook, stirring for 3-5 mins, until the veg is starting to soften. Stir in the curry powder, then the tomatoes and bouillon. Tip in the cauliflower florets, coriander stems and raisins, then cover and simmer for 10 mins over a medium-low heat.\r\n\r\nSTEP 4\r\nDrain the rice, then tip it into the veg mixture and gently toss to combine. Cover and cook over a low heat for 8 mins until the rice and cauliflower are tender. Try not to add extra liquid, as you don’t want the end result to be wet. Remove from the heat and leave to stand for 5 mins, then gently toss through the cashews and coriander leaves. Will keep chilled for two days. Leave to cool completely first. Reheat portions in the microwave until piping hot before serving.', 60, 'vegan_biryani.png', 4, 1, 1, 500, 0),
(14, 'Easy roast leg of lamb', 'Dinner', '2kg-2.5kg leg of lamb\nsmall bunch rosemary, cut into 2cm short sprigs\n4-5 garlic cloves, finely sliced\n4-6 brown anchovy fillets, chopped (optional)\n2 tbsp olive oil\n2 large onions, thickly sliced', 'STEP 1\nHeat the oven to 220C/200C fan/gas 7. Remove all packaging from the lamb and keep a note of the weight to calculate the roasting time. Make incisions into the lamb using a small sharp knife, at an angle, about 5cm into the meat. Making the holes at an angle will mean more of the meat is flavoured, and the flavourings will be less tempted to be pushed out when roasting.\n\nSTEP 2\nInsert a short sprig of rosemary, a garlic slice and a small piece of anchovy (if using) into each of the holes, pressing in well with your finger. Season the meat all over with salt and pepper, then rub the lamb all over with the olive oil. Transfer to a roasting tin, sat on the onion slices. Roast for 20 mins.\n\nSTEP 3\nLower the oven to 190C/170C fan/gas 5 and cook for another 15-20 mins per 500g (1 hr – 1 hr 20 mins for 2kg leg) depending on how pink you like your lamb. You can also check the internal temperature of the lamb if you prefer pinker meat – it will be 55C for medium (pink) and 70C for well done.\n\nSTEP 4\nCover with foil and rest for 15 mins before carving. Save the tray juices and onion slices to make gravy.', 120, 'roast_lamb.png', 5, 0, 0, 473, 0),
(15, 'Breakfast burrito', 'Breakfast', '1 tsp chipotle paste\n1 egg\n1 tsp rapeseed oil\n50g kale\n7 cherry tomatoes, halved\n½ small avocado, sliced\n1 wholemeal tortilla wrap, warmed', 'STEP 1\nWhisk the chipotle paste with the egg and some seasoning in a jug. Heat the oil in a large frying pan, add the kale and tomatoes.\n\nSTEP 2\nCook until the kale is wilted and the tomatoes have softened, then push everything to the side of the pan. Pour the beaten egg into the cleared half of the pan and scramble. Layer everything into the centre of your wrap, topping with the avocado, then wrap up and eat immediately.', 15, 'breakfast_burrito.png', 1, 0, 1, 366, 0),
(16, 'Breakfast muffins', 'Breakfast', '2 large eggs\n150ml pot natural low-fat yogurt\n50ml rapeseed oil\n100g apple sauce or pureed apples (find with the baby food)\n1 ripe banana, mashed\n4 tbsp clear honey\n1 tsp vanilla extract\n200g wholemeal flour\n50g rolled oats, plus extra for sprinkling\n1½ tsp baking powder\n1½ tsp bicarbonate of soda\n1½ tsp cinnamon\n100g blueberry\n2 tbsp mixed seed (we used pumpkin, sunflower and flaxseed)', 'STEP 1\r\nHeat oven to 180C/160C fan/gas 4. Line a 12-hole muffin tin with 12 large muffin cases. In a jug, mix the eggs, yogurt, oil, apple sauce, banana, honey and vanilla. Tip the remaining ingredients, except the seeds, into a large bowl, add a pinch of salt and mix to combine.\r\n\r\nSTEP 2\r\nPour the wet ingredients into the dry and mix briefly until you have a smooth batter – don’t overmix as this will make the muffins heavy. Divide the batter between the cases. Sprinkle the muffins with the extra oats and the seeds. Bake for 25-30 mins until golden and well risen, and a skewer inserted into the centre of a muffin comes out clean. Remove from the oven, transfer to a wire rack and leave to cool. Can be stored in a sealed container for up to 3 days.', 45, 'breakfast_muffin.png', 12, 0, 0, 179, 0);

-- --------------------------------------------------------

--
-- Table structure for table `meal_plan`
--

CREATE TABLE `meal_plan` (
  `meal_plan_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `meal_id` int(11) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `meal_plan`
--

INSERT INTO `meal_plan` (`meal_plan_id`, `user_id`, `meal_id`, `date`) VALUES
(157, 19, 1, '2024-04-29'),
(158, 19, 16, '2024-04-30');

-- --------------------------------------------------------

--
-- Table structure for table `shopping_list`
--

CREATE TABLE `shopping_list` (
  `shopping_list_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `ingredient_id` int(11) NOT NULL,
  `shopping_list_meal_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shopping_list`
--

INSERT INTO `shopping_list` (`shopping_list_id`, `user_id`, `ingredient_id`, `shopping_list_meal_id`) VALUES
(1668, 19, 1, 157),
(1669, 19, 2, 157),
(1670, 19, 3, 157),
(1671, 19, 4, 157),
(1672, 19, 5, 157),
(1673, 19, 6, 157),
(1674, 19, 7, 157),
(1675, 19, 8, 157),
(1676, 19, 9, 157),
(1677, 19, 10, 157),
(1678, 19, 11, 157),
(1679, 19, 12, 157),
(1680, 19, 13, 157),
(1681, 19, 14, 157),
(1682, 19, 15, 157),
(1683, 19, 16, 157),
(1684, 19, 174, 158),
(1685, 19, 175, 158),
(1686, 19, 176, 158),
(1687, 19, 177, 158),
(1688, 19, 178, 158),
(1689, 19, 179, 158),
(1690, 19, 180, 158),
(1691, 19, 181, 158),
(1692, 19, 182, 158),
(1693, 19, 183, 158),
(1694, 19, 184, 158),
(1695, 19, 185, 158),
(1696, 19, 186, 158),
(1697, 19, 187, 158);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `email`, `username`, `password`) VALUES
(14, 'sully@gmail.com', 'Sully', '$2y$10$A.aLaB9O6t2zgc8.rqNsi.w9U8GW.8NcHXednSmUWFCe2mHR5QitK'),
(15, 'tester@gmail.com', 'tester', '$2y$10$CsGuKb3E3LtsvCuNF4f.n.P6lOQ94bik8wdn8RM2sNnXHhG2kUjwK'),
(16, 'sully@gmail.com', 'Sully', '$2y$10$JxataN.MwyAZTsWPs.o03eMFv.3UIO9PpTTpgFZFgAeci8Z9ooSZG'),
(17, 'sully1@gmail.com', 'sully', '$2y$10$2xXW7zluKhXTHoDojbcHXuMsAQ1bORaPqTD0n.iP4gWw8WJk05yvC'),
(18, 'tester1@gmail.com', 'tester', '$2y$10$mMmqaZ0LDFFJGXkURmJNpO.88GbG.Jgr7PA4qeuXdLgezj5ETshbW'),
(19, 'test@', 'test', '$2y$10$NbrHvYZdUfaBgSgKO1Th.Oew3tUlr7jF.Jn9DDWBLuWZ42zaoaidq'),
(20, 'test12@', 'test1', '$2y$10$ZfMaxN82v12mOICXJ9ETTOmd22wLGKevFdrzRSw5jHpCVRuGBsNji'),
(21, 'uai@', 'Username', '$2y$10$7lTXsemQ/EtDS8YodrtKvu52Ae4FOJU8oBk7ZcsutYDKbD01PcRjm');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `custom_ingredients`
--
ALTER TABLE `custom_ingredients`
  ADD PRIMARY KEY (`custom_ingredient_id`),
  ADD KEY `custom_user_id` (`custom_user_id`);

--
-- Indexes for table `favourites`
--
ALTER TABLE `favourites`
  ADD PRIMARY KEY (`favourites_id`),
  ADD KEY `user_favourite_id` (`user_favourite_id`),
  ADD KEY `meal_favourite_id` (`meal_favourite_id`);

--
-- Indexes for table `ingredients`
--
ALTER TABLE `ingredients`
  ADD PRIMARY KEY (`ingredient_id`),
  ADD KEY `meal_ingredient_id` (`meal_ingredient_id`);

--
-- Indexes for table `meal`
--
ALTER TABLE `meal`
  ADD PRIMARY KEY (`meal_id`);

--
-- Indexes for table `meal_plan`
--
ALTER TABLE `meal_plan`
  ADD PRIMARY KEY (`meal_plan_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `meals_id` (`meal_id`);

--
-- Indexes for table `shopping_list`
--
ALTER TABLE `shopping_list`
  ADD PRIMARY KEY (`shopping_list_id`),
  ADD KEY `users_id` (`user_id`),
  ADD KEY `custom_ingredient_id` (`ingredient_id`),
  ADD KEY `shopping_list_meal_id` (`shopping_list_meal_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `custom_ingredients`
--
ALTER TABLE `custom_ingredients`
  MODIFY `custom_ingredient_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `favourites`
--
ALTER TABLE `favourites`
  MODIFY `favourites_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `ingredients`
--
ALTER TABLE `ingredients`
  MODIFY `ingredient_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=229;

--
-- AUTO_INCREMENT for table `meal`
--
ALTER TABLE `meal`
  MODIFY `meal_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `meal_plan`
--
ALTER TABLE `meal_plan`
  MODIFY `meal_plan_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=159;

--
-- AUTO_INCREMENT for table `shopping_list`
--
ALTER TABLE `shopping_list`
  MODIFY `shopping_list_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1698;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `custom_ingredients`
--
ALTER TABLE `custom_ingredients`
  ADD CONSTRAINT `custom_user_id` FOREIGN KEY (`custom_user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `favourites`
--
ALTER TABLE `favourites`
  ADD CONSTRAINT `meal_favourite_id` FOREIGN KEY (`meal_favourite_id`) REFERENCES `meal` (`meal_id`),
  ADD CONSTRAINT `user_favourite_id` FOREIGN KEY (`user_favourite_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `ingredients`
--
ALTER TABLE `ingredients`
  ADD CONSTRAINT `meal_ingredient_id` FOREIGN KEY (`meal_ingredient_id`) REFERENCES `meal` (`meal_id`);

--
-- Constraints for table `meal_plan`
--
ALTER TABLE `meal_plan`
  ADD CONSTRAINT `meals_id` FOREIGN KEY (`meal_id`) REFERENCES `meal` (`meal_id`),
  ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `shopping_list`
--
ALTER TABLE `shopping_list`
  ADD CONSTRAINT `ingredient_id` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`ingredient_id`),
  ADD CONSTRAINT `shopping_list_meal_id` FOREIGN KEY (`shopping_list_meal_id`) REFERENCES `meal_plan` (`meal_plan_id`),
  ADD CONSTRAINT `users_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
