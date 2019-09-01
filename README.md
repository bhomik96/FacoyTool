# FacoyTool

## This project is the part of Software Engineering Lab 2019.<br>

<br><br>
This project is an eclipse plugin to an open source tool,Facoy. Facoy ﬁnds similar java code snippets from various online sources like stackoverflow.But Facoy currently works as a website. So to faciliate developers so that they can extract code snippets from their IDE only, we created this ecplipse plugin for the Facoy app.The project was completed using Jsoup and Java. <br><br>We added diﬀerent Syntax Similarity techniques to the above tool and provided our own similarity score to the snippets based on present techniques like Cosine and Jaccard Similarity. We further added the functionalities of another open source tool, nlp2code which searches its dataset of code snippets based on the natural language statement provided by the developer in the IDE like "sort function" to search for code snippets related to sorting in Java.<br><br>
To sum it up, the plugin provide features to developers to view relevant code snippets either by quering their code or their natural language query and further applies similarity measures in it to sort the results in the most relevant manner for the developer.<br><br>
### nlp2code folder
contains final code and documentation.<br>
### org.plugin.x folder
contains release 1 code.<br>
