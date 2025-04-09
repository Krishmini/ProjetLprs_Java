-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 09 avr. 2025 à 10:01
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `lprs_javafx`
--

-- --------------------------------------------------------

--
-- Structure de la table `action_logs`
--

DROP TABLE IF EXISTS `action_logs`;
CREATE TABLE IF NOT EXISTS `action_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `utilisateur_id` int DEFAULT NULL,
  `action_type` varchar(50) DEFAULT NULL,
  `objet_modifie` varchar(50) DEFAULT NULL,
  `details_action` text,
  `date_heure_action` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `utilisateur_id` (`utilisateur_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `action_logs`
--

INSERT INTO `action_logs` (`id`, `utilisateur_id`, `action_type`, `objet_modifie`, `details_action`, `date_heure_action`) VALUES
(1, 1, 'Connexion', 'utilisateur', 'Connexion réussie', '2025-03-25 13:13:06'),
(2, 2, 'Création', 'utilisateur', 'Ajout d\'un nouvel étudiant', '2025-04-06 18:54:13');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `id_commande` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `heure` time NOT NULL,
  `ref_utilisateur` int NOT NULL,
  `ref_fournisseur` int NOT NULL,
  PRIMARY KEY (`id_commande`),
  KEY `fk_commande_utilisateur` (`ref_utilisateur`),
  KEY `fk_commande_fournisseur` (`ref_fournisseur`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_commande`, `date`, `heure`, `ref_utilisateur`, `ref_fournisseur`) VALUES
(1, '2024-08-11', '14:47:00', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `connexion_logs`
--

DROP TABLE IF EXISTS `connexion_logs`;
CREATE TABLE IF NOT EXISTS `connexion_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `utilisateur_id` int NOT NULL,
  `date_heure_connexion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_connexion_utilisateur` (`utilisateur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `connexion_logs`
--

INSERT INTO `connexion_logs` (`id`, `utilisateur_id`, `date_heure_connexion`, `action`) VALUES
(1, 1, '2025-04-05 21:23:53', 'Connexion réussie'),
(2, 1, '2025-04-05 21:25:03', 'Connexion réussie'),
(3, 1, '2025-04-05 21:30:26', 'Connexion réussie'),
(4, 1, '2025-04-06 08:53:36', 'Connexion réussie'),
(5, 1, '2025-04-06 09:03:42', 'Connexion réussie'),
(6, 1, '2025-04-06 09:22:24', 'Connexion réussie'),
(7, 1, '2025-04-06 09:57:06', 'Connexion réussie'),
(8, 1, '2025-04-06 10:35:19', 'Connexion réussie'),
(9, 1, '2025-04-06 10:45:55', 'Connexion réussie'),
(10, 1, '2025-04-06 10:49:22', 'Connexion réussie'),
(11, 1, '2025-04-06 10:51:43', 'Connexion réussie'),
(12, 1, '2025-04-06 10:58:47', 'Connexion réussie'),
(13, 1, '2025-04-06 11:05:24', 'Connexion réussie'),
(14, 1, '2025-04-06 11:07:59', 'Connexion réussie'),
(15, 1, '2025-04-06 11:10:32', 'Connexion réussie'),
(16, 1, '2025-04-06 11:17:59', 'Connexion réussie'),
(17, 1, '2025-04-06 11:51:45', 'Connexion réussie'),
(18, 1, '2025-04-06 11:59:43', 'Connexion réussie'),
(19, 1, '2025-04-06 12:12:26', 'Connexion réussie'),
(20, 1, '2025-04-06 12:14:50', 'Connexion réussie'),
(21, 2, '2025-04-06 18:54:49', 'Connexion réussie'),
(22, 2, '2025-04-06 20:36:00', 'Connexion réussie'),
(23, 2, '2025-04-06 20:38:48', 'Connexion réussie'),
(24, 2, '2025-04-06 20:41:43', 'Connexion réussie'),
(25, 2, '2025-04-06 20:42:44', 'Connexion réussie'),
(26, 2, '2025-04-06 20:47:53', 'Connexion réussie'),
(27, 2, '2025-04-06 21:13:20', 'Connexion réussie');

-- --------------------------------------------------------

--
-- Structure de la table `demandefourniture`
--

DROP TABLE IF EXISTS `demandefourniture`;
CREATE TABLE IF NOT EXISTS `demandefourniture` (
  `id_demandeFourniture` int NOT NULL AUTO_INCREMENT,
  `article` varchar(50) NOT NULL,
  `quantite` int NOT NULL,
  `raison` varchar(255) NOT NULL,
  `approuver` tinyint(1) DEFAULT NULL,
  `ref_stock` int DEFAULT NULL,
  `ref_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_demandeFourniture`),
  KEY `fk_demandeFourniture_stock` (`ref_stock`),
  KEY `fk_demandeFourniture_utilisateur` (`ref_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `demandefourniture`
--

INSERT INTO `demandefourniture` (`id_demandeFourniture`, `article`, `quantite`, `raison`, `approuver`, `ref_stock`, `ref_utilisateur`) VALUES
(5, 'Ecran ordinateur', 0, 'gjh,vh,cgnhfcnc', 1, 1, 1),
(7, 'Cable VGA', 10, 'CDSKBDN.CJH?NC X', 1, NULL, 1);

--
-- Déclencheurs `demandefourniture`
--
DROP TRIGGER IF EXISTS `after_demandeFourniture_update`;
DELIMITER $$
CREATE TRIGGER `after_demandeFourniture_update` AFTER UPDATE ON `demandefourniture` FOR EACH ROW BEGIN
   
    IF NEW.approuver = '1' THEN
        
        IF NEW.ref_stock IS NOT NULL THEN
            UPDATE stock
            SET nombre_stock = nombre_stock + NEW.quantite
            WHERE id_stock = NEW.ref_stock;
        ELSE
        
            INSERT INTO stock (libelle, nombre_stock, description, ref_fournisseur)
            VALUES (NEW.article, NEW.quantite, NEW.raison, 1); -- 
        END IF;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `dossierinscription`
--

DROP TABLE IF EXISTS `dossierinscription`;
CREATE TABLE IF NOT EXISTS `dossierinscription` (
  `id_dossierInscription` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `heure` time NOT NULL,
  `filiere_interet` varchar(255) NOT NULL,
  `motivation_etudiant` varchar(255) NOT NULL,
  `ref_utilisateur` int NOT NULL,
  `ref_ficheEtudiant` int NOT NULL,
  PRIMARY KEY (`id_dossierInscription`),
  KEY `fk_dossierInscription_utilisateur` (`ref_utilisateur`),
  KEY `fk_dossierInscription_ficheEtudiant` (`ref_ficheEtudiant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ficheetudiant`
--

DROP TABLE IF EXISTS `ficheetudiant`;
CREATE TABLE IF NOT EXISTS `ficheetudiant` (
  `id_ficheEtudiant` int NOT NULL AUTO_INCREMENT,
  `nom_e` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `prenom_e` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dernier_diplome` varchar(50) NOT NULL,
  `email_e` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `telephone_e` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ville_e` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cp_e` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rue_e` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ref_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_ficheEtudiant`),
  KEY `fk_ficheEtudiant_utilisateur` (`ref_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `fournisseur`
--

DROP TABLE IF EXISTS `fournisseur`;
CREATE TABLE IF NOT EXISTS `fournisseur` (
  `id_fournisseur` int NOT NULL AUTO_INCREMENT,
  `nom_f` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `prix` int NOT NULL,
  PRIMARY KEY (`id_fournisseur`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `fournisseur`
--

INSERT INTO `fournisseur` (`id_fournisseur`, `nom_f`, `prix`) VALUES
(1, 'Les Bonnes Affaires', 0),
(2, 'TechMatériel', 24),
(3, 'Bureau Plus', 10),
(4, 'Équipements Sud', 18),
(5, 'Nord Matos', 21),
(6, 'Fournitures Pro', 15);

-- --------------------------------------------------------

--
-- Structure de la table `quantite`
--

DROP TABLE IF EXISTS `quantite`;
CREATE TABLE IF NOT EXISTS `quantite` (
  `id_quantite` int NOT NULL AUTO_INCREMENT,
  `quantite` int NOT NULL,
  `ref_stock` int NOT NULL,
  `ref_commande` int NOT NULL,
  PRIMARY KEY (`id_quantite`),
  KEY `fk_quantite_stock` (`ref_stock`),
  KEY `fk_quantite_commande` (`ref_commande`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `quantite`
--

INSERT INTO `quantite` (`id_quantite`, `quantite`, `ref_stock`, `ref_commande`) VALUES
(1, 5, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `rendezvous`
--

DROP TABLE IF EXISTS `rendezvous`;
CREATE TABLE IF NOT EXISTS `rendezvous` (
  `id_rendezVous` int NOT NULL AUTO_INCREMENT,
  `salle` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `heure` time NOT NULL,
  `ref_utilisateur` int NOT NULL,
  `ref_dossierInscription` int NOT NULL,
  PRIMARY KEY (`id_rendezVous`),
  KEY `fk_rendezVous_utilisateur` (`ref_utilisateur`),
  KEY `fk_rendezVous_dossierInscription` (`ref_dossierInscription`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `id_stock` int NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  `nombre_stock` int NOT NULL,
  `description` varchar(255) NOT NULL,
  `ref_fournisseur` int DEFAULT NULL,
  PRIMARY KEY (`id_stock`),
  KEY `fk_stock_fournisseur` (`ref_fournisseur`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `stock`
--

INSERT INTO `stock` (`id_stock`, `libelle`, `nombre_stock`, `description`, `ref_fournisseur`) VALUES
(1, 'Ecran ordinateur', 3, 'Ecran d\'ordinateur à cable VGA', 1),
(2, 'Cable VGA', 10, 'CDSKBDN.CJH?NC X', 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `role` varchar(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `mail` varchar(100) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  PRIMARY KEY (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `role`, `nom`, `prenom`, `mail`, `mdp`) VALUES
(1, 'professeur', 'Abdallah', 'Sherazad', 'sherazad.abdallah@gmail.com', 'javaFX=compliquer'),
(2, 'Gestionnaire', 'Franki', 'Blanco', 'franki.blanco@gmail.fr', 'mystrategy');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `fk_commande_fournisseur` FOREIGN KEY (`ref_fournisseur`) REFERENCES `fournisseur` (`id_fournisseur`),
  ADD CONSTRAINT `fk_commande_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`);

--
-- Contraintes pour la table `demandefourniture`
--
ALTER TABLE `demandefourniture`
  ADD CONSTRAINT `fk_demandeFourniture_stock` FOREIGN KEY (`ref_stock`) REFERENCES `stock` (`id_stock`),
  ADD CONSTRAINT `fk_demandeFourniture_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`);

--
-- Contraintes pour la table `dossierinscription`
--
ALTER TABLE `dossierinscription`
  ADD CONSTRAINT `fk_dossierInscription_ficheEtudiant` FOREIGN KEY (`ref_ficheEtudiant`) REFERENCES `ficheetudiant` (`id_ficheEtudiant`),
  ADD CONSTRAINT `fk_dossierInscription_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`);

--
-- Contraintes pour la table `ficheetudiant`
--
ALTER TABLE `ficheetudiant`
  ADD CONSTRAINT `fk_ficheEtudiant_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`);

--
-- Contraintes pour la table `quantite`
--
ALTER TABLE `quantite`
  ADD CONSTRAINT `fk_quantite_commande` FOREIGN KEY (`ref_commande`) REFERENCES `commande` (`id_commande`),
  ADD CONSTRAINT `fk_quantite_stock` FOREIGN KEY (`ref_stock`) REFERENCES `stock` (`id_stock`);

--
-- Contraintes pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD CONSTRAINT `fk_rendezVous_dossierInscription` FOREIGN KEY (`ref_dossierInscription`) REFERENCES `dossierinscription` (`id_dossierInscription`),
  ADD CONSTRAINT `fk_rendezVous_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`);

--
-- Contraintes pour la table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `fk_stock_fournisseur` FOREIGN KEY (`ref_fournisseur`) REFERENCES `fournisseur` (`id_fournisseur`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
