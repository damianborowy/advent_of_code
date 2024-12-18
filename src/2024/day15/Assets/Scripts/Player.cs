using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public class Player : MonoBehaviour
{
    public float moveSpeed = 20f;
    public bool isMoving = false;
    public Vector2 lastPlayerMove;
    
    private Vector2 moveDirection;
    private List<Box> _boxes;
    
    public IEnumerator MovePlayer(Vector2 direction)
    {
        if (!CanPlayerMove(direction)) yield break;
        
        var targetPosition = (Vector2)transform.position + direction;
        lastPlayerMove = direction;
        
        yield return MoveToPosition(targetPosition);
    }

    public void FixedUpdate()
    {
        if (isMoving) return;
        
        moveDirection = Vector2.zero;
        if (Input.GetKeyDown(KeyCode.W)) moveDirection = Vector2.up;
        if (Input.GetKeyDown(KeyCode.S)) moveDirection = Vector2.down;
        if (Input.GetKeyDown(KeyCode.A)) moveDirection = Vector2.left;
        if (Input.GetKeyDown(KeyCode.D)) moveDirection = Vector2.right;

        if (moveDirection != Vector2.zero)
        {
            Vector2 targetPosition = (Vector2)transform.position + moveDirection;
            StartCoroutine(MoveToPosition(targetPosition));
        }
    }

    public bool CanPlayerMove(Vector2 direction)
    {
        var hitObjects = Physics2D.RaycastAll(transform.position, direction);

        var wall = hitObjects.First(obj => obj.collider.gameObject.CompareTag("Wall"));
        var wallDistance = Mathf.RoundToInt(wall.distance) - 1;

        var boxesBeforeWall = hitObjects
            .Where(obj => obj.collider.gameObject.CompareTag("Box") && obj.distance < wallDistance);

        return wallDistance > boxesBeforeWall.Count();
    }
    
    public void SetBoxes(List<Box> boxes) => _boxes = boxes;

    public IEnumerator MoveToPosition(Vector2 targetPosition)
    {
        isMoving = true;
        
        while ((Vector2)transform.position != targetPosition)
        {
            transform.position = Vector2.MoveTowards(transform.position, targetPosition, moveSpeed * Time.deltaTime);
            yield return null;
        }

        isMoving = false;
    }
}
