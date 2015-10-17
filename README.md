ToDO
- REMOVE Exception
- Copy header around
- update disclaimer
- js always saying save!! | its fine
- change material css to blue-red

- download stats with timestamp
- able to load different question sets
- candidate summary put in card
- rename finalize to something
- add Logo
- add Tag line
- Modify instrictions and disclaimer

- Timer?

Fallbacks ===
- power backup

Other ===========
Put finalize-confirm page content in card
Put finalize page content in card




DONE =========================================================================
// Aux function
x get list of all users currently registered users
x get the current questions loaded with Key
x get the current questions loaded without key
x get the result of the exam
	x list of candidates with their
		x details [name/id/dob] 
		x total score
		x score per category
		x how many attended, how many correct, per category
		x sort by height marker

x Move all maps to LinkedHashMap
x Add QuestionCache Entry for 	<questionId vs Question>
	x Then store only the <questionNo vs questionId> in candidate
	x fetch the question and answer from cache whenever needed.
x change MongoDb port number
x find a mongo db editor
x change font to Roboto
x Log IP Address of Candidate
x make all the resources local