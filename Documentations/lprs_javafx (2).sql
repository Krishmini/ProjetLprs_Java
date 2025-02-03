-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 26 jan. 2025 à 22:53
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `approuver` varchar(50) NOT NULL,
  `ref_stock` int NOT NULL,
  `ref_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_demandeFourniture`),
  KEY `fk_demandeFourniture_stock` (`ref_stock`),
  KEY `fk_demandeFourniture_utilisateur` (`ref_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `ref_fournisseur` int NOT NULL,
  PRIMARY KEY (`id_stock`),
  KEY `fk_stock_fournisseur` (`ref_fournisseur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
