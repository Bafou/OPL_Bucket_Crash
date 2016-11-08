# OPL_Bucket_Crash

Les fichiers en entrée sont au format :
#[Numéro entrée] [Offset Stack*] in [Méthode]/?? ([Input méthode])/() from/at [Classe*] [Variables locales*]/No locals.


Avec at donnant nom classe précis, suivit de ligne après ":"
Les informations supplémentaires sont au format : [variable] = [valeur]\n
No locals est affiché si on est avec un at et qu'il n'y a pas de variables locales

Output : 
[test] -> [bucket associé]
