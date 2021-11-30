# ift3325_tp2

Pour tester le code sur windows :

- ouvrir 2 fenetres cmd

- écrire dans les 2 : cd <le dossier avec les classes Java>

- écrire dans l'un : java Receiver <#port>
  - il va attendre une connexion

- écrire dans l'autre : java Sender 127.0.0.1 <#port> test.txt
  - va se connecter au même #port

Ensuite seront affichées les trames échangées par Receiver et Sender dans leur fenêtres cmd respectives.
