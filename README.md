ToDO
- make all the resources local
- Log IP Address of Candidate
- REMOVE Exception
- Copy header around
- update disclaimer

Fallbacks ===
- power backup

Issues ======
- change font to Roboto
- js always saying save!!
- change material css to blue-red


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